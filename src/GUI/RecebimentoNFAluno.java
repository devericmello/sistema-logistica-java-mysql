package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;

public class RecebimentoNFAluno extends JFrame {

    private JTextField txtNomeNF, txtDescNF;
    private JComboBox<KeyValue> cbEstoque, cbMaterial;
    private JSpinner spQtd;
    private JFormattedTextField txtCusto;
    private JTable tblItens, tblNFs;
    private DefaultTableModel itensModel, nfModel;

    public RecebimentoNFAluno() {
        setTitle("Recebimento por Nota Fiscal — Treino");
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
                carregarResumoNFs();
            } catch (Exception e) { showErr(e); }
        });
    }

    private JPanel buildTop() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new TitledBorder("Cabeçalho da NF e Estoque de Destino"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);

        c.gridy=0; c.gridx=0; p.add(new JLabel("Nome NF:"), c);
        txtNomeNF = new JTextField(20); c.gridx=1; p.add(txtNomeNF, c);
        c.gridx=2; p.add(new JLabel("Descrição:"), c);
        txtDescNF = new JTextField(28); c.gridx=3; p.add(txtDescNF, c);

        c.gridy=1; c.gridx=0; p.add(new JLabel("Estoque:"), c);
        cbEstoque = new JComboBox<>(); cbEstoque.setPreferredSize(new Dimension(260,28)); c.gridx=1; p.add(cbEstoque, c);

        c.gridx=2; p.add(new JLabel("Material:"), c);
        cbMaterial = new JComboBox<>(); cbMaterial.setPreferredSize(new Dimension(260,28)); c.gridx=3; p.add(cbMaterial, c);

        c.gridy=2; c.gridx=0; p.add(new JLabel("Quantidade:"), c);
        spQtd = new JSpinner(new SpinnerNumberModel(1,1,1_000_000,1));
        ((JSpinner.DefaultEditor) spQtd.getEditor()).getTextField().setColumns(8);
        c.gridx=1; p.add(spQtd, c);

        c.gridx=2; p.add(new JLabel("Custo unitário:"), c);
        txtCusto = new JFormattedTextField(NumberFormat.getNumberInstance()); txtCusto.setColumns(10); txtCusto.setValue(0.00);
        c.gridx=3; p.add(txtCusto, c);
        return p;
    }

    private JSplitPane buildCenter() {
        itensModel = new DefaultTableModel(new Object[]{"ID Mat.","Material","Qtd","Custo Unit."},0){
            public boolean isCellEditable(int r,int c){return false;}
            public Class<?> getColumnClass(int c){return switch(c){case 0,2->Integer.class; case 3->Double.class; default->String.class;};}
        };
        tblItens = new JTable(itensModel);
        JScrollPane spItens = new JScrollPane(tblItens);
        spItens.setBorder(new TitledBorder("Itens da NF (pendentes)"));

        nfModel = new DefaultTableModel(new Object[]{"ID NF","Nome","Itens","Qtd Total","Valor Total"},0){
            public boolean isCellEditable(int r,int c){return false;}
            public Class<?> getColumnClass(int c){return switch(c){case 0,2,3->Integer.class; case 4->Double.class; default->String.class;};}
        };
        tblNFs = new JTable(nfModel);
        JScrollPane spNF = new JScrollPane(tblNFs);
        spNF.setBorder(new TitledBorder("Notas Fiscais gravadas"));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spItens, spNF);
        split.setResizeWeight(0.55);
        return split;
    }

    private JPanel buildBottom() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAddItem = new JButton("Adicionar item"); btnAddItem.addActionListener(e -> addItem());
        JButton btnRemoverItem = new JButton("Remover selecionado"); btnRemoverItem.addActionListener(e -> removerItem());
        JButton btnConfirmar = new JButton("Confirmar Recebimento"); btnConfirmar.addActionListener(e -> confirmar());
        JButton btnRecarregar = new JButton("Recarregar resumo"); btnRecarregar.addActionListener(e -> carregarResumoNFs());
        JButton btnLimpar = new JButton("Limpar itens"); btnLimpar.addActionListener(e -> itensModel.setRowCount(0));
        JButton btnFechar = new JButton("Fechar"); btnFechar.addActionListener(e -> dispose());
        p.add(btnAddItem); p.add(btnRemoverItem); p.add(btnConfirmar); p.add(btnRecarregar); p.add(btnLimpar); p.add(btnFechar);
        return p;
    }

    // ===== Fluxo =====
    private void addItem() {
        KeyValue mat = (KeyValue) cbMaterial.getSelectedItem();
        if (mat == null) { JOptionPane.showMessageDialog(this,"Cadastre/seleciona material."); return; }
        int qtd = ((Number) spQtd.getValue()).intValue();
        try { txtCusto.commitEdit(); } catch (Exception ignore) {}
        Number n = (Number) txtCusto.getValue();
        double custo = n==null?0.0:n.doubleValue();
        if (qtd<=0 || custo<0) { JOptionPane.showMessageDialog(this,"Quantidade e custo válidos."); return; }
        itensModel.addRow(new Object[]{mat.id(), mat.label(), qtd, custo});
    }

    private void removerItem() {
        int r = tblItens.getSelectedRow();
        if (r>=0) itensModel.removeRow(r);
    }

    private void confirmar() {
        if (itensModel.getRowCount()==0) { JOptionPane.showMessageDialog(this,"Inclua ao menos um item."); return; }
        KeyValue est = (KeyValue) cbEstoque.getSelectedItem();
        if (est==null) { JOptionPane.showMessageDialog(this,"Selecione um estoque."); return; }

        String nomeNF = txtNomeNF.getText().isBlank()?("NF Treino "+ LocalDateTime.now()):txtNomeNF.getText();
        String descNF = txtDescNF.getText();

        String sqlNF = "INSERT INTO nota_fiscal(nome, descricao) VALUES(?,?)";
        String sqlItem = """
            INSERT INTO nota_fiscal_item(id_nota_fiscal, id_material, id_estoque, quantidade, custo_unitario)
            VALUES (?,?,?,?,?)
        """;
        String sqlMov = """
            INSERT INTO movimentacao_material(id_estoque,id_material,tipo,quantidade,custo_unitario,motivo)
            VALUES (?,?,?,?,?,'Entrada por NF')
        """;

        try (Connection cn = getConnection()) {
            cn.setAutoCommit(false);
            int idNF;
            try (PreparedStatement ps = cn.prepareStatement(sqlNF, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nomeNF); ps.setString(2, descNF); ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); idNF = rs.getInt(1); }
            }

            try (PreparedStatement psi = cn.prepareStatement(sqlItem);
                 PreparedStatement psm = cn.prepareStatement(sqlMov)) {
                for (int i=0;i<itensModel.getRowCount();i++){
                    int idMat = (Integer) itensModel.getValueAt(i,0);
                    int qtd   = (Integer) itensModel.getValueAt(i,2);
                    double custo = ((Number) itensModel.getValueAt(i,3)).doubleValue();

                    psi.setInt(1,idNF); psi.setInt(2,idMat); psi.setInt(3,est.id()); psi.setInt(4,qtd);
                    psi.setBigDecimal(5, BigDecimal.valueOf(custo)); psi.addBatch();

                    psm.setInt(1, est.id()); psm.setInt(2, idMat); psm.setString(3,"E"); psm.setInt(4,qtd);
                    psm.setBigDecimal(5, BigDecimal.valueOf(custo)); psm.addBatch();
                }
                psi.executeBatch(); psm.executeBatch();
            }

            cn.commit();
            itensModel.setRowCount(0); txtNomeNF.setText(""); txtDescNF.setText("");
            carregarResumoNFs();
            JOptionPane.showMessageDialog(this,"Recebimento gravado.");
        } catch (Exception e) { showErr(e); }
    }

    private void carregarResumoNFs() {
        nfModel.setRowCount(0);
        String q = """
            SELECT nf.id_nota_fiscal, nf.nome,
                   COUNT(ni.id_nota_fiscal) AS itens,
                   COALESCE(SUM(ni.quantidade),0) AS qtd_total,
                   COALESCE(SUM(ni.quantidade*ni.custo_unitario),0) AS valor_total
            FROM nota_fiscal nf
            LEFT JOIN nota_fiscal_item ni ON ni.id_nota_fiscal=nf.id_nota_fiscal
            GROUP BY nf.id_nota_fiscal, nf.nome
            ORDER BY nf.id_nota_fiscal DESC
        """;
        try (Connection cn = getConnection(); PreparedStatement ps = cn.prepareStatement(q); ResultSet rs = ps.executeQuery()){
            while (rs.next()) nfModel.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5)});
        } catch (Exception e) { showErr(e); }
    }

    // ===== Schema =====
    private void ensureSchema() throws SQLException {
        try (Connection cn = getConnection(); Statement st = cn.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS nota_fiscal_item (
                  id_item INT AUTO_INCREMENT PRIMARY KEY,
                  id_nota_fiscal INT NOT NULL,
                  id_material INT NOT NULL,
                  id_estoque INT NOT NULL,
                  quantidade INT NOT NULL,
                  custo_unitario DECIMAL(10,2) NOT NULL,
                  CONSTRAINT fk_ni_nf      FOREIGN KEY (id_nota_fiscal) REFERENCES nota_fiscal(id_nota_fiscal),
                  CONSTRAINT fk_ni_mat     FOREIGN KEY (id_material) REFERENCES material(id_material),
                  CONSTRAINT fk_ni_estoque FOREIGN KEY (id_estoque)  REFERENCES estoque(id_estoque)
                )
            """);
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

    private void carregarEstoques() throws SQLException {
        cbEstoque.removeAllItems();
        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT id_estoque, COALESCE(descricao,'(sem)') FROM estoque ORDER BY 2");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) cbEstoque.addItem(new KeyValue(rs.getInt(1), rs.getString(2)));
        }
    }

    private void carregarMateriais() throws SQLException {
        cbMaterial.removeAllItems();
        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT id_material, COALESCE(nome,'Material') FROM material ORDER BY 2");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) cbMaterial.addItem(new KeyValue(rs.getInt(1), rs.getString(2)));
        }
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

    public static void main(String[] args){ SwingUtilities.invokeLater(() -> new RecebimentoNFAluno().setVisible(true)); }
}
