package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SimuladorEstoqueAluno extends JFrame {

    private JComboBox<KeyValue> cbEstoque, cbMaterial;
    private JComboBox<String> cbTipo;
    private JSpinner spQuantidade;
    private JFormattedTextField txtCustoUnit;
    private JTextField txtMotivo;
    private JTable tblSaldo, tblHistorico;
    private DefaultTableModel saldoModel, histModel;

    public SimuladorEstoqueAluno() {
        setTitle("Simulador de Estoque — Aluno");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buildTop(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildBottom(), BorderLayout.SOUTH);

        SwingUtilities.invokeLater(() -> {
            try {
                ensureSchema();
                seedEstoquesAddMissing();
                seedMateriaisAddMissing();
                carregarEstoques();
                carregarMateriais();
                recarregarGrids();
            } catch (Exception ex) { showErr(ex); }
        });
    }

    private JPanel buildTop() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new TitledBorder("Parâmetros da Movimentação"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);

        c.gridy=0; c.gridx=0; p.add(new JLabel("Estoque:"), c);
        cbEstoque = new JComboBox<>(); cbEstoque.setPreferredSize(new Dimension(260,28)); c.gridx=1; p.add(cbEstoque, c);

        c.gridx=2; p.add(new JLabel("Material:"), c);
        cbMaterial = new JComboBox<>(); cbMaterial.setPreferredSize(new Dimension(260,28)); c.gridx=3; p.add(cbMaterial, c);

        c.gridy=1; c.gridx=0; p.add(new JLabel("Tipo:"), c);
        cbTipo = new JComboBox<>(new String[]{"Entrada","Saída"}); c.gridx=1; p.add(cbTipo, c);

        c.gridx=2; p.add(new JLabel("Quantidade:"), c);
        spQuantidade = new JSpinner(new SpinnerNumberModel(1,1,1_000_000,1));
        ((JSpinner.DefaultEditor) spQuantidade.getEditor()).getTextField().setColumns(8);
        c.gridx=3; p.add(spQuantidade, c);

        c.gridy=2; c.gridx=0; p.add(new JLabel("Custo Unit. (Entrada):"), c);
        txtCustoUnit = new JFormattedTextField(java.text.NumberFormat.getNumberInstance());
        txtCustoUnit.setColumns(10); txtCustoUnit.setValue(0.00);
        c.gridx=1; p.add(txtCustoUnit, c);

        c.gridx=2; p.add(new JLabel("Motivo:"), c);
        txtMotivo = new JTextField(22); c.gridx=3; p.add(txtMotivo, c);

        return p;
    }

    private JSplitPane buildCenter() {
        saldoModel = new DefaultTableModel(new Object[]{"ID","Material","Saldo","ValorEntradas"},0){
            public boolean isCellEditable(int r,int c){return false;}
            public Class<?> getColumnClass(int c){return switch(c){case 0,2->Integer.class;case 3->Double.class;default->String.class;};}
        };
        tblSaldo = new JTable(saldoModel);
        JScrollPane spSaldo = new JScrollPane(tblSaldo);
        spSaldo.setBorder(new TitledBorder("Saldos do Estoque Selecionado"));

        histModel = new DefaultTableModel(new Object[]{"Data/Hora","Material","Tipo","Qtd","CustoUnit","Motivo"},0){
            public boolean isCellEditable(int r,int c){return false;}
        };
        tblHistorico = new JTable(histModel);
        JScrollPane spHist = new JScrollPane(tblHistorico);
        spHist.setBorder(new TitledBorder("Histórico de Movimentações (30)"));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spSaldo, spHist);
        split.setResizeWeight(0.6);
        return split;
    }

    private JPanel buildBottom() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRegistrar = new JButton("Registrar"); btnRegistrar.addActionListener(e -> registrarMovimento());
        JButton btnRecarregar = new JButton("Recarregar"); btnRecarregar.addActionListener(e -> recarregarGrids());
        JButton btnReset = new JButton("Reset simulação"); btnReset.addActionListener(e -> resetSimulacao());
        JButton btnFechar = new JButton("Fechar"); btnFechar.addActionListener(e -> dispose());
        p.add(btnRegistrar); p.add(btnRecarregar); p.add(btnReset); p.add(btnFechar);
        return p;
    }

    // ===== Schema =====
    private void ensureSchema() throws SQLException {
        try (Connection cn = getConnection(); Statement st = cn.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS movimentacao_material (
                  id_mov INT AUTO_INCREMENT PRIMARY KEY,
                  id_estoque INT NOT NULL,
                  id_material INT NOT NULL,
                  tipo ENUM('E','S') NOT NULL,
                  quantidade INT NOT NULL,
                  custo_unitario DECIMAL(10,2) NULL,
                  data_movimentacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                  motivo VARCHAR(60) NOT NULL,
                  CONSTRAINT fk_mm_estoque  FOREIGN KEY (id_estoque)  REFERENCES estoque(id_estoque),
                  CONSTRAINT fk_mm_material FOREIGN KEY (id_material) REFERENCES material(id_material)
                )
            """);
        }
    }

    // ===== Seed incremental =====
    private void seedEstoquesAddMissing() throws SQLException {
        String[][] est = {
          {"Depósito A","Bloco 1"},{"Depósito B","Bloco 2"},{"Depósito C","Bloco 3"},
          {"Almoxarifado Central","Prédio 1"},{"Picking 01","Rua 01"},{"Picking 02","Rua 02"},
          {"Picking 03","Rua 03"},{"Picking 04","Rua 04"},{"Cross-docking","Docas 01"},
          {"Quarentena","Sala QA"},{"Devoluções","Docas 02"},{"Expedição","Docas 03"},
          {"Recebimento","Docas 00"},{"WIP Produção","Linha 2"},{"Materiais Perigosos","Galpão HAZ"},
          {"Itens de Alto Valor","Cofre"},{"Frigorificado","Câmara Fria"},{"Mezanino","MZN-1"}
        };
        try (Connection cn = getConnection();
             PreparedStatement chk = cn.prepareStatement("SELECT 1 FROM estoque WHERE descricao=? LIMIT 1");
             PreparedStatement ins = cn.prepareStatement("INSERT INTO estoque(descricao, localizacao) VALUES(?,?)")) {
            for (String[] e : est) {
                chk.setString(1, e[0]);
                try (ResultSet rs = chk.executeQuery()) {
                    if (!rs.next()) { ins.setString(1, e[0]); ins.setString(2, e[1]); ins.addBatch(); }
                }
            }
            ins.executeBatch();
        }
    }

    private void seedMateriaisAddMissing() throws SQLException {
        String[] mats = {
          "Pallet PBR","Pallet Euro","Caixa Plástica 30L","Caixa Plástica 60L","Caixa Plástica 80L",
          "Contentor Gradeado","Big Bag 1t","Saco Ráfia","Filme Stretch","Fita PP 12mm","Fita PET 16mm",
          "Cantoneira Papelão","Lacre Segurança","Etiqueta RFID UHF","Etiqueta Código Barras",
          "Envelope Manifesto","Plástico Bolha","Dessecante Sílica","Cartucho Térmico Zebra",
          "Tinta Inkjet Linerless","Colete EPI","Luva Nitrílica","Capacete EPI","Protetor Auricular",
          "Parafuso M8","Porca M8","Arruela M8","Kit Embalagem E-commerce","Manual Operacional",
          "Kit Picking Lista","Tubo PVC 50mm","Abraçadeira Nylon","Rolo Stretch Mini","Cinta Cat.6",
          "Caixa Papelão 40x30x20","Caixa Papelão 60x40x40","Tampa Caixa 60L","Divisória Caixa",
          "Bobina Papel Kraft","Bobina POF","Selo Metálico 16mm","Talão CTE","Formulário Devolução",
          "Seladora Manual","Etiquetadora Manual","Porta Pallet Reposição","Marcador Chão 5cm",
          "Sinalização Doca","Calço de Roda"
        };
        try (Connection cn = getConnection();
             PreparedStatement chk = cn.prepareStatement("SELECT 1 FROM material WHERE nome=? LIMIT 1");
             PreparedStatement ins = cn.prepareStatement("INSERT INTO material(nome, valor_total) VALUES(?,0)")) {
            for (String m : mats) {
                chk.setString(1, m);
                try (ResultSet rs = chk.executeQuery()) {
                    if (!rs.next()) { ins.setString(1, m); ins.addBatch(); }
                }
            }
            ins.executeBatch();
        }
    }

    // ===== Fluxo =====
    private void carregarEstoques() throws SQLException {
        cbEstoque.removeAllItems();
        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT id_estoque, COALESCE(descricao,'(sem)') FROM estoque ORDER BY 2");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) cbEstoque.addItem(new KeyValue(rs.getInt(1), rs.getString(2)));
        }
        cbEstoque.addActionListener(e -> recarregarGrids());
    }

    private void carregarMateriais() throws SQLException {
        cbMaterial.removeAllItems();
        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT id_material, COALESCE(nome,'Material') FROM material ORDER BY 2");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) cbMaterial.addItem(new KeyValue(rs.getInt(1), rs.getString(2)));
        }
    }

    private void recarregarGrids() {
        KeyValue est = (KeyValue) cbEstoque.getSelectedItem();
        if (est == null) return;

        saldoModel.setRowCount(0);
        String sqlSaldo = """
            SELECT m.id_material, m.nome,
                   COALESCE(SUM(CASE WHEN mm.tipo='E' THEN mm.quantidade ELSE -mm.quantidade END),0) AS saldo,
                   COALESCE(SUM(CASE WHEN mm.tipo='E' THEN mm.quantidade * COALESCE(mm.custo_unitario,0) ELSE 0 END),0) AS valorEntradas
            FROM material m
            LEFT JOIN movimentacao_material mm ON mm.id_material=m.id_material AND mm.id_estoque=?
            GROUP BY m.id_material, m.nome
            ORDER BY m.nome
        """;
        try (Connection cn = getConnection(); PreparedStatement ps = cn.prepareStatement(sqlSaldo)) {
            ps.setInt(1, est.id());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    saldoModel.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4)});
            }
        } catch (Exception ex) { showErr(ex); }

        histModel.setRowCount(0);
        String sqlHist = """
            SELECT mm.data_movimentacao, m.nome, mm.tipo, mm.quantidade, mm.custo_unitario, mm.motivo
            FROM movimentacao_material mm
            JOIN material m ON m.id_material=mm.id_material
            WHERE mm.id_estoque=? ORDER BY mm.data_movimentacao DESC LIMIT 30
        """;
        try (Connection cn = getConnection(); PreparedStatement ps = cn.prepareStatement(sqlHist)) {
            ps.setInt(1, est.id());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    histModel.addRow(new Object[]{
                            rs.getTimestamp(1).toLocalDateTime(),
                            rs.getString(2),
                            "E".equals(rs.getString(3)) ? "Entrada" : "Saída",
                            rs.getInt(4),
                            rs.getBigDecimal(5),
                            rs.getString(6)
                    });
                }
            }
        } catch (Exception ex) { showErr(ex); }
    }

    private void registrarMovimento() {
        KeyValue est = (KeyValue) cbEstoque.getSelectedItem();
        KeyValue mat = (KeyValue) cbMaterial.getSelectedItem();
        if (est == null || mat == null) { JOptionPane.showMessageDialog(this,"Selecione estoque e material."); return; }
        String tipo = cbTipo.getSelectedItem().toString().startsWith("Entrada") ? "E" : "S";
        int qtd = ((Number) spQuantidade.getValue()).intValue();
        double custo = 0.0;
        try { txtCustoUnit.commitEdit(); Number n = (Number) txtCustoUnit.getValue(); custo = n==null?0.0:n.doubleValue(); } catch (Exception ignore){}
        String motivo = txtMotivo.getText().isBlank() ? (tipo.equals("E")?"Treino: entrada":"Treino: saída") : txtMotivo.getText();

        if ("S".equals(tipo)) {
            int saldoAtual = obterSaldoMaterial(est.id(), mat.id());
            if (qtd > saldoAtual) {
                int opt = JOptionPane.showConfirmDialog(this,"Saída maior que saldo ("+saldoAtual+"). Continuar?","Confirmar",JOptionPane.YES_NO_OPTION);
                if (opt != JOptionPane.YES_OPTION) return;
            }
        }

        String sql = "INSERT INTO movimentacao_material(id_estoque,id_material,tipo,quantidade,custo_unitario,motivo) VALUES(?,?,?,?,?,?)";
        try (Connection cn = getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, est.id()); ps.setInt(2, mat.id()); ps.setString(3, tipo); ps.setInt(4, qtd);
            if ("E".equals(tipo)) ps.setBigDecimal(5, java.math.BigDecimal.valueOf(custo)); else ps.setNull(5, Types.DECIMAL);
            ps.setString(6, motivo);
            ps.executeUpdate();
            recarregarGrids();
            txtMotivo.setText("");
        } catch (Exception ex) { showErr(ex); }
    }

    private int obterSaldoMaterial(int idEstoque, int idMaterial) {
        String q = "SELECT COALESCE(SUM(CASE WHEN tipo='E' THEN quantidade ELSE -quantidade END),0) FROM movimentacao_material WHERE id_estoque=? AND id_material=?";
        try (Connection cn = getConnection(); PreparedStatement ps = cn.prepareStatement(q)) {
            ps.setInt(1, idEstoque); ps.setInt(2, idMaterial);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        } catch (Exception ex) { showErr(ex); }
        return 0;
    }

    private void resetSimulacao() {
        KeyValue est = (KeyValue) cbEstoque.getSelectedItem();
        if (est == null) return;
        if (JOptionPane.showConfirmDialog(this,"Apagar TODAS as movimentações deste estoque?","Confirmar",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) return;
        try (Connection cn = getConnection(); PreparedStatement ps = cn.prepareStatement("DELETE FROM movimentacao_material WHERE id_estoque=?")) {
            ps.setInt(1, est.id()); ps.executeUpdate(); recarregarGrids();
        } catch (Exception ex) { showErr(ex); }
    }

    private Connection getConnection() throws SQLException {
        try {
            Class<?> cf = Class.forName("ConnectionFactory");
            try { return (Connection) cf.getMethod("getConnection").invoke(null); }
            catch (NoSuchMethodException e) {
                Object inst = cf.getDeclaredConstructor().newInstance();
                return (Connection) cf.getMethod("getConnection").invoke(inst);
            }
        } catch (Throwable ignore) {
            String url = "jdbc:mysql://localhost:3306/logistica?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            return DriverManager.getConnection(url,"root","");
        }
    }

    private void showErr(Throwable ex){ ex.printStackTrace(); JOptionPane.showMessageDialog(this, ex.getClass().getSimpleName()+": "+ex.getMessage()); }
    private record KeyValue(int id, String label){ public String toString(){ return label+"  [#"+id+"]"; } }

    public static void main(String[] args){ SwingUtilities.invokeLater(() -> new SimuladorEstoqueAluno().setVisible(true)); }
}
