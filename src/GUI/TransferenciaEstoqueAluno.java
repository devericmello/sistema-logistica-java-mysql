package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TransferenciaEstoqueAluno extends JFrame {

    private JComboBox<KeyValue> cbOrigem, cbDestino, cbMaterial;
    private JSpinner spQtd;
    private JTextField txtMotivo;
    private JTable tblSaldoOrigem, tblSaldoDestino, tblHistorico;
    private DefaultTableModel saldoOModel, saldoDModel, histModel;

    public TransferenciaEstoqueAluno() {
        setTitle("Transferência entre Estoques — Treino");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

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
            } catch (Exception e) { showErr(e); }
        });
    }

    private JPanel buildTop() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new TitledBorder("Parâmetros da Transferência"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);

        c.gridy=0; c.gridx=0; p.add(new JLabel("Origem:"), c);
        cbOrigem = new JComboBox<>(); cbOrigem.setPreferredSize(new Dimension(260,28)); c.gridx=1; p.add(cbOrigem, c);

        c.gridx=2; p.add(new JLabel("Destino:"), c);
        cbDestino = new JComboBox<>(); cbDestino.setPreferredSize(new Dimension(260,28)); c.gridx=3; p.add(cbDestino, c);

        c.gridy=1; c.gridx=0; p.add(new JLabel("Material:"), c);
        cbMaterial = new JComboBox<>(); cbMaterial.setPreferredSize(new Dimension(260,28)); c.gridx=1; p.add(cbMaterial, c);

        c.gridx=2; p.add(new JLabel("Quantidade:"), c);
        spQtd = new JSpinner(new SpinnerNumberModel(1,1,1_000_000,1));
        ((JSpinner.DefaultEditor) spQtd.getEditor()).getTextField().setColumns(8);
        c.gridx=3; p.add(spQtd, c);

        c.gridy=2; c.gridx=0; p.add(new JLabel("Motivo:"), c);
        txtMotivo = new JTextField(32); c.gridx=1; c.gridwidth=3; p.add(txtMotivo, c); c.gridwidth=1;

        return p;
    }

    private JSplitPane buildCenter() {
        saldoOModel = new DefaultTableModel(new Object[]{"ID","Material","Saldo"},0){
            public boolean isCellEditable(int r,int c){return false;}
            public Class<?> getColumnClass(int c){return switch(c){case 0,2->Integer.class; default->String.class;};}
        };
        tblSaldoOrigem = new JTable(saldoOModel);
        JScrollPane spO = new JScrollPane(tblSaldoOrigem);
        spO.setBorder(new TitledBorder("Saldos — Origem"));

        saldoDModel = new DefaultTableModel(new Object[]{"ID","Material","Saldo"},0){
            public boolean isCellEditable(int r,int c){return false;}
            public Class<?> getColumnClass(int c){return switch(c){case 0,2->Integer.class; default->String.class;};}
        };
        tblSaldoDestino = new JTable(saldoDModel);
        JScrollPane spD = new JScrollPane(tblSaldoDestino);
        spD.setBorder(new TitledBorder("Saldos — Destino"));

        JSplitPane left = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, spO, spD);
        left.setResizeWeight(0.5);

        histModel = new DefaultTableModel(new Object[]{"Data/Hora","Estoque","Material","Tipo","Qtd","Motivo"},0){
            public boolean isCellEditable(int r,int c){return false;}
        };
        tblHistorico = new JTable(histModel);
        JScrollPane spH = new JScrollPane(tblHistorico);
        spH.setBorder(new TitledBorder("Histórico recente (50)"));

        JSplitPane top = new JSplitPane(JSplitPane.VERTICAL_SPLIT, left, spH);
        top.setResizeWeight(0.55);
        return top;
    }

    private JPanel buildBottom() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnTransferir = new JButton("Transferir"); btnTransferir.addActionListener(e -> transferir());
        JButton btnRecarregar = new JButton("Recarregar"); btnRecarregar.addActionListener(e -> recarregarGrids());
        JButton btnFechar = new JButton("Fechar"); btnFechar.addActionListener(e -> dispose());
        p.add(btnTransferir); p.add(btnRecarregar); p.add(btnFechar);
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
        cbOrigem.removeAllItems(); cbDestino.removeAllItems();
        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT id_estoque, COALESCE(descricao,'(sem)') FROM estoque ORDER BY 2");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KeyValue kv = new KeyValue(rs.getInt(1), rs.getString(2));
                cbOrigem.addItem(kv); cbDestino.addItem(kv);
            }
        }
        cbOrigem.addActionListener(e -> recarregarGrids());
        cbDestino.addActionListener(e -> recarregarGrids());
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
        KeyValue o = (KeyValue) cbOrigem.getSelectedItem();
        KeyValue d = (KeyValue) cbDestino.getSelectedItem();
        if (o == null || d == null) return;

        // saldos origem
        saldoOModel.setRowCount(0);
        fillSaldoForEstoque(o.id(), saldoOModel);

        // saldos destino
        saldoDModel.setRowCount(0);
        fillSaldoForEstoque(d.id(), saldoDModel);

        // histórico último 50
        histModel.setRowCount(0);
        String q = """
            SELECT mm.data_movimentacao, e.descricao, m.nome, mm.tipo, mm.quantidade, mm.motivo
            FROM movimentacao_material mm
            JOIN estoque e  ON e.id_estoque=mm.id_estoque
            JOIN material m ON m.id_material=mm.id_material
            ORDER BY mm.data_movimentacao DESC
            LIMIT 50
        """;
        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(q);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                histModel.addRow(new Object[]{
                        rs.getTimestamp(1).toLocalDateTime(),
                        rs.getString(2),
                        rs.getString(3),
                        "E".equals(rs.getString(4)) ? "Entrada" : "Saída",
                        rs.getInt(5),
                        rs.getString(6)
                });
            }
        } catch (Exception ex) { showErr(ex); }
    }

    private void fillSaldoForEstoque(int idEstoque, DefaultTableModel model) {
        String sql = """
            SELECT m.id_material, m.nome,
                   COALESCE(SUM(CASE WHEN mm.tipo='E' THEN mm.quantidade ELSE -mm.quantidade END),0) AS saldo
            FROM material m
            LEFT JOIN movimentacao_material mm
              ON mm.id_material=m.id_material AND mm.id_estoque=?
            GROUP BY m.id_material, m.nome
            ORDER BY m.nome
        """;
        try (Connection cn = getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idEstoque);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getInt(3)});
            }
        } catch (Exception ex) { showErr(ex); }
    }

    private void transferir() {
        KeyValue o = (KeyValue) cbOrigem.getSelectedItem();
        KeyValue d = (KeyValue) cbDestino.getSelectedItem();
        KeyValue m = (KeyValue) cbMaterial.getSelectedItem();
        if (o == null || d == null || m == null) { JOptionPane.showMessageDialog(this,"Selecione origem, destino e material."); return; }
        if (o.id() == d.id()) { JOptionPane.showMessageDialog(this,"Origem e destino devem ser diferentes."); return; }
        int qtd = ((Number) spQtd.getValue()).intValue();
        if (qtd <= 0) { JOptionPane.showMessageDialog(this,"Quantidade inválida."); return; }

        int saldo = obterSaldoMaterial(o.id(), m.id());
        if (qtd > saldo) {
            int opt = JOptionPane.showConfirmDialog(this,
                    "Saída maior que o saldo de origem ("+saldo+"). Continuar?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opt != JOptionPane.YES_OPTION) return;
        }

        String motivoS = txtMotivo.getText().isBlank() ? ("Transf. para "+d.label()) : txtMotivo.getText();
        String motivoE = txtMotivo.getText().isBlank() ? ("Transf. de "+o.label()) : txtMotivo.getText();

        String sql = "INSERT INTO movimentacao_material(id_estoque,id_material,tipo,quantidade,custo_unitario,motivo) VALUES(?,?,?,?,?,?)";
        try (Connection cn = getConnection()) {
            cn.setAutoCommit(false);
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                // saída origem
                ps.setInt(1, o.id()); ps.setInt(2, m.id()); ps.setString(3, "S");
                ps.setInt(4, qtd); ps.setNull(5, Types.DECIMAL); ps.setString(6, motivoS); ps.addBatch();
                // entrada destino
                ps.setInt(1, d.id()); ps.setInt(2, m.id()); ps.setString(3, "E");
                ps.setInt(4, qtd); ps.setNull(5, Types.DECIMAL); ps.setString(6, motivoE); ps.addBatch();
                ps.executeBatch();
            }
            cn.commit();
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

    // ===== infra =====
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

    public static void main(String[] args){ SwingUtilities.invokeLater(() -> new TransferenciaEstoqueAluno().setVisible(true)); }
}
