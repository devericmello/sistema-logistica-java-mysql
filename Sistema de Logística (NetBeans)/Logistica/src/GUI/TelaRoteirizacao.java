// src/GUI/TelaRoteirizacao.java
package GUI;

import Factory.ConnectionFactory;
import service.RoteirizacaoServicePro;
import service.RoteirizacaoServicePro.Parada;
import service.RoteirizacaoServicePro.ResultadoRota;
import service.RoteirizacaoServicePro.Objetivo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class TelaRoteirizacao extends JFrame {

    // UI
    private JPanel pnlRoot, pnlTopo;
    private JTextField txtDataRota;
    private JComboBox<String> cbObjetivo, cbVeiculo;
    private JButton btnRecarregar, btnGerarRotaOtimizada, btnSalvarRota, btnCompararMinhaRota, btnEnviarParaEntrega;
    private JSplitPane split;
    private JTable tblPedidosRota, tblParadas;
    private JButton btnAdd, btnRemove, btnUp, btnDown;
    private JLabel lblResumo;

    // Estado
    private final Connection conn;
    private final RoteirizacaoServicePro service = new RoteirizacaoServicePro();

    // Origem e parâmetros
    private int idEstoqueOrigem = 0;
    private double latOrigem = 0.0, lonOrigem = 0.0;
    private double velocidadeKmh = 35, tempoParadaMin = 8;
    private double custoHora = 80, custoKm = 3.5;
    private double capPesoKg = 0, capVolM3 = 0;

    public TelaRoteirizacao() { this(ConnectionFactory.getConnection()); }

    public TelaRoteirizacao(Connection conn) {
        super("Roteirização — Treino");
        this.conn = conn;
        montarUI();
        wireEvents();
        carregarPedidosComCoordenadas();
        carregarVeiculos();
        carregarOrigemEstoque();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1200, 720);
        setLocationRelativeTo(null);
    }

    private void montarUI() {
        pnlRoot = new JPanel(new BorderLayout(6,6)); setContentPane(pnlRoot);

        pnlTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtDataRota = new JTextField(java.time.LocalDate.now().toString(), 10);
        cbObjetivo = new JComboBox<>(new String[]{"MENOR_TEMPO","MENOR_CUSTO"});
        cbVeiculo  = new JComboBox<>();
        btnRecarregar = new JButton("Recarregar pedidos");
        btnGerarRotaOtimizada = new JButton("Sugerir rota (NN+2opt)");
        btnSalvarRota = new JButton("Salvar rota");
        btnCompararMinhaRota = new JButton("Comparar minha rota");
        btnEnviarParaEntrega = new JButton("Enviar para entrega");

        pnlTopo.add(new JLabel("Data rota:")); pnlTopo.add(txtDataRota);
        pnlTopo.add(new JLabel("Objetivo:")); pnlTopo.add(cbObjetivo);
        pnlTopo.add(new JLabel("Veículo:"));  pnlTopo.add(cbVeiculo);
        pnlTopo.add(btnRecarregar); pnlTopo.add(btnGerarRotaOtimizada);
        pnlTopo.add(btnSalvarRota); pnlTopo.add(btnCompararMinhaRota); pnlTopo.add(btnEnviarParaEntrega);
        pnlRoot.add(pnlTopo, BorderLayout.NORTH);

        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); split.setResizeWeight(0.5);

        tblPedidosRota = new JTable(new DefaultTableModel(new Object[]{"ID Pedido","Cliente","Lat","Lon"},0){
            public boolean isCellEditable(int r,int c){return false;}
        });
        split.setLeftComponent(new JScrollPane(tblPedidosRota));

        JPanel right = new JPanel(new BorderLayout(4,4));
        tblParadas = new JTable(new DefaultTableModel(new Object[]{"Seq","ID Pedido","Lat","Lon"},0){
            public boolean isCellEditable(int r,int c){return false;}
        });
        right.add(new JScrollPane(tblParadas), BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdd = new JButton(">> Adicionar");
        btnRemove = new JButton("<< Remover");
        btnUp = new JButton("↑");
        btnDown = new JButton("↓");
        controls.add(btnAdd); controls.add(btnRemove);
        controls.add(new JLabel("Mover:")); controls.add(btnUp); controls.add(btnDown);
        right.add(controls, BorderLayout.NORTH);

        lblResumo = new JLabel("Resumo: --");
        right.add(lblResumo, BorderLayout.SOUTH);

        split.setRightComponent(right);
        pnlRoot.add(split, BorderLayout.CENTER);
    }

    private void wireEvents() {
        btnRecarregar.addActionListener(e -> carregarPedidosComCoordenadas());
        btnAdd.addActionListener(e -> moverSelecionados(tblPedidosRota, tblParadas));
        btnRemove.addActionListener(e -> removerSelecionados(tblParadas));
        btnUp.addActionListener(e -> moverLinha(tblParadas, -1));
        btnDown.addActionListener(e -> moverLinha(tblParadas, +1));
        btnGerarRotaOtimizada.addActionListener(e -> onGerarOtimizada());
        btnCompararMinhaRota.addActionListener(e -> onComparar());
        btnSalvarRota.addActionListener(e -> onSalvarRota());
        btnEnviarParaEntrega.addActionListener(e -> onEnviarParaEntrega());
        cbVeiculo.addActionListener(e -> carregarParametrosVeiculoSelecionado());
    }

    // ---- Data access ----

    private void carregarPedidosComCoordenadas() {
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(
                "SELECT p.id_pedido, c.nome, c.latitude, c.longitude " +
                "FROM pedido p JOIN cliente c ON c.id_cliente=p.id_cliente " +
                "WHERE p.status IN ('ABERTO','SEPARACAO','EXPEDIDO') " +
                "AND c.latitude IS NOT NULL AND c.longitude IS NOT NULL " +
                "ORDER BY p.id_pedido")) {
            DefaultTableModel m = (DefaultTableModel) tblPedidosRota.getModel();
            m.setRowCount(0);
            while (rs.next()) m.addRow(new Object[]{ rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4) });
        } catch (Exception ex) { erro("Falha ao carregar pedidos/coords: " + ex.getMessage()); }
    }

    private void carregarVeiculos() {
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(
                "SELECT id_veiculo, placa FROM veiculo ORDER BY id_veiculo")) {
            cbVeiculo.removeAllItems();
            while (rs.next()) cbVeiculo.addItem(rs.getInt(1) + " - " + rs.getString(2));
        } catch (Exception ignore) {}
        if (cbVeiculo.getItemCount() > 0) cbVeiculo.setSelectedIndex(0);
        carregarParametrosVeiculoSelecionado();
    }

    private void carregarOrigemEstoque() {
        try {
            idEstoqueOrigem = obterEstoqueOrigemDefault();
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT latitude, longitude FROM estoque WHERE id_estoque=?")) {
                ps.setInt(1, idEstoqueOrigem);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        double la = rs.getDouble(1); boolean n1 = rs.wasNull();
                        double lo = rs.getDouble(2); boolean n2 = rs.wasNull();
                        if (n1 || n2) info("Defina latitude/longitude do estoque de origem (tabela 'estoque').");
                        else { latOrigem = la; lonOrigem = lo; }
                    }
                }
            }
        } catch (Exception ex) { erro("Falha ao carregar origem do estoque: " + ex.getMessage()); }
    }

    private void carregarParametrosVeiculoSelecionado() {
        try {
            String veic = (String) cbVeiculo.getSelectedItem();
            if (veic == null) return;
            int idVeiculo = Integer.parseInt(veic.split(" - ")[0]);
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT IFNULL(velocidade_media_kmh,0), IFNULL(custo_hora,0), IFNULL(custo_km,0), " +
                    "IFNULL(capacidade_peso_kg,0), IFNULL(capacidade_vol_m3,0) " +
                    "FROM veiculo WHERE id_veiculo=?")) {
                ps.setInt(1, idVeiculo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        double v  = rs.getDouble(1); if (v  > 0) velocidadeKmh = v;
                        double ch = rs.getDouble(2); if (ch > 0) custoHora     = ch;
                        double ck = rs.getDouble(3); if (ck > 0) custoKm       = ck;
                        capPesoKg = rs.getDouble(4);
                        capVolM3  = rs.getDouble(5);
                    }
                }
            }
        } catch (Exception ignore) {}
    }

    private int obterEstoqueOrigemDefault() throws Exception {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT id_estoque FROM estoque ORDER BY id_estoque LIMIT 1")) {
            if (rs.next()) return rs.getInt(1);
        }
        throw new IllegalStateException("Cadastre um estoque de origem.");
    }

    // ---- UI helpers ----

    private void moverSelecionados(JTable origem, JTable destino) {
        DefaultTableModel mo = (DefaultTableModel) origem.getModel();
        DefaultTableModel md = (DefaultTableModel) destino.getModel();
        int[] sel = origem.getSelectedRows();
        for (int r : sel)
            md.addRow(new Object[]{ md.getRowCount()+1, mo.getValueAt(r,0), mo.getValueAt(r,2), mo.getValueAt(r,3) });
    }

    private void removerSelecionados(JTable table) {
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        int[] sel = table.getSelectedRows();
        for (int i=sel.length-1;i>=0;i--) m.removeRow(sel[i]);
        for (int r=0;r<m.getRowCount();r++) m.setValueAt(r+1, r, 0);
    }

    private void moverLinha(JTable t, int delta) {
        int r = t.getSelectedRow(); if (r < 0) return;
        int dest = r + delta; if (dest < 0 || dest >= t.getRowCount()) return;
        DefaultTableModel m = (DefaultTableModel) t.getModel();
        Object[] row = new Object[m.getColumnCount()];
        for (int c=0;c<m.getColumnCount();c++) row[c] = m.getValueAt(r,c);
        m.removeRow(r); m.insertRow(dest, row);
        for (int i=0;i<m.getRowCount();i++) m.setValueAt(i+1, i, 0);
        t.setRowSelectionInterval(dest,dest);
    }

    private List<Parada> getParadasDaTabela(JTable t) {
        DefaultTableModel m = (DefaultTableModel) t.getModel();
        List<Parada> list = new ArrayList<>();
        for (int r=0;r<m.getRowCount();r++) {
            int id = Integer.parseInt(String.valueOf(m.getValueAt(r,1)));
            double lat = Double.parseDouble(String.valueOf(m.getValueAt(r,2)));
            double lon = Double.parseDouble(String.valueOf(m.getValueAt(r,3)));
            list.add(new Parada(id, lat, lon));
        }
        return list;
    }

    private List<Parada> getTodasParadasDisponiveis() {
        DefaultTableModel m = (DefaultTableModel) tblPedidosRota.getModel();
        List<Parada> list = new ArrayList<>();
        for (int r=0;r<m.getRowCount();r++) {
            int id = Integer.parseInt(String.valueOf(m.getValueAt(r,0)));
            double lat = Double.parseDouble(String.valueOf(m.getValueAt(r,2)));
            double lon = Double.parseDouble(String.valueOf(m.getValueAt(r,3)));
            list.add(new Parada(id, lat, lon));
        }
        return list;
    }

    private Objetivo objetivoSelecionado() {
        String s = String.valueOf(cbObjetivo.getSelectedItem());
        return "MENOR_TEMPO".equals(s) ? Objetivo.MENOR_TEMPO : Objetivo.MENOR_CUSTO;
    }

    // ---- Ações ----

    private void onGerarOtimizada() {
        carregarOrigemEstoque();
        carregarParametrosVeiculoSelecionado();

        List<Parada> todas = getTodasParadasDisponiveis();
        if (todas.isEmpty()) { info("Nenhum pedido com coordenadas."); return; }

        ResultadoRota r = service.gerarRota(
                latOrigem, lonOrigem, todas,
                objetivoSelecionado(),
                velocidadeKmh, tempoParadaMin,
                custoHora, custoKm,
                capPesoKg, capVolM3);

        DefaultTableModel m = (DefaultTableModel) tblParadas.getModel();
        m.setRowCount(0);
        int seq = 1;
        for (Integer idPed : r.ordemPedidos) {
            Parada p = todas.stream().filter(pp -> pp.idPedido == idPed).findFirst().orElse(null);
            if (p != null) m.addRow(new Object[]{seq++, p.idPedido, p.lat, p.lon});
        }
        lblResumo.setText(String.format("Sugestão: %.2f km | %d min | R$ %.2f | %d paradas",
                r.distanciaTotalKm, r.tempoPrevistoMin, r.custoPrevisto, r.ordemPedidos.size()));
    }

    private void onComparar() {
        carregarOrigemEstoque();
        carregarParametrosVeiculoSelecionado();

        List<Parada> minha = getParadasDaTabela(tblParadas);
        if (minha.isEmpty()) { info("Monte sua rota no painel da direita."); return; }
        List<Parada> todas = getTodasParadasDisponiveis();

        Map<String, Double> cmp = service.compararRotaAluno(
                latOrigem, lonOrigem,
                minha, todas,
                objetivoSelecionado(),
                velocidadeKmh, tempoParadaMin,
                custoHora, custoKm);

        lblResumo.setText(String.format(
                "Aluno: %.2f km | %.0f min | R$ %.2f  ||  Ótima: %.2f km | %.0f min | R$ %.2f  ||  Δ: %.2f km | %.0f min | R$ %.2f",
                cmp.get("km_aluno"), cmp.get("min_aluno"), cmp.get("custo_aluno"),
                cmp.get("km_otimizada"), cmp.get("min_otimizada"), cmp.get("custo_otimizado"),
                cmp.get("delta_km"), cmp.get("delta_min"), cmp.get("delta_custo")));
    }

    private int criarRota(String data, int idEstoque, String objetivo, String algoritmo,
                          double custoPrev, int tempoPrevMin, double distPrevKm) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO rota (data_rota,id_estoque_origem,objetivo,algoritmo,custo_previsto,tempo_total_previsto_min,distancia_total_prevista_km,criado_por) " +
                "VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, data); ps.setInt(2, idEstoque);
            ps.setString(3, objetivo); ps.setString(4, algoritmo);
            ps.setBigDecimal(5, java.math.BigDecimal.valueOf(custoPrev));
            ps.setInt(6, tempoPrevMin);
            ps.setBigDecimal(7, java.math.BigDecimal.valueOf(distPrevKm));
            ps.setString(8, "tela-roteirizacao"); ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
        }
        throw new IllegalStateException("Não foi possível criar a rota.");
    }

    private void onSalvarRota() {
        try {
            String data = txtDataRota.getText().trim();
            if (data.isBlank()) data = java.time.LocalDate.now().toString();

            // avalia a sequência atual (se não houver, km=0)
            List<Parada> seq = getParadasDaTabela(tblParadas);
            RoteirizacaoServicePro.ResultadoRota resumo =
                    service.avaliarRota(latOrigem, lonOrigem, seq, velocidadeKmh, tempoParadaMin, custoHora, custoKm);

            int idRota = criarRota(data, idEstoqueOrigem,
                    String.valueOf(cbObjetivo.getSelectedItem()),
                    "NN_2OPT",
                    resumo.custoPrevisto, resumo.tempoPrevistoMin, resumo.distanciaTotalKm);

            DefaultTableModel m = (DefaultTableModel) tblParadas.getModel();
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rota_parada (id_rota,id_pedido,id_cliente,sequencia,latitude,longitude) " +
                    "SELECT ?, p.id_pedido, p.id_cliente, ?, c.latitude, c.longitude " +
                    "FROM pedido p JOIN cliente c ON c.id_cliente=p.id_cliente WHERE p.id_pedido=?")) {
                for (int r=0;r<m.getRowCount();r++) {
                    int seqN = (Integer) m.getValueAt(r,0);
                    int idPed = Integer.parseInt(String.valueOf(m.getValueAt(r,1)));
                    ps.setInt(1, idRota); ps.setInt(2, seqN); ps.setInt(3, idPed); ps.addBatch();
                }
                ps.executeBatch();
            }
            info("Rota salva. ID = " + idRota);
        } catch (Exception ex) { erro("Falha ao salvar rota: " + ex.getMessage()); }
    }

    private void onEnviarParaEntrega() {
        try {
            String veic = (String) cbVeiculo.getSelectedItem();
            if (veic == null) { info("Cadastre veículo na tabela 'veiculo'."); return; }
            int idVeiculo = Integer.parseInt(veic.split(" - ")[0]);

            String data = txtDataRota.getText().trim();
            if (data.isBlank()) data = java.time.LocalDate.now().toString();

            List<Parada> seq = getParadasDaTabela(tblParadas);
            RoteirizacaoServicePro.ResultadoRota resumo =
                    service.avaliarRota(latOrigem, lonOrigem, seq, velocidadeKmh, tempoParadaMin, custoHora, custoKm);

            int idRota = criarRota(data, idEstoqueOrigem,
                    String.valueOf(cbObjetivo.getSelectedItem()),
                    "MANUAL",
                    resumo.custoPrevisto, resumo.tempoPrevistoMin, resumo.distanciaTotalKm);

            DefaultTableModel m = (DefaultTableModel) tblParadas.getModel();
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO entrega (id_pedido,id_rota,id_veiculo,sequencia,status) VALUES (?,?,?,?, 'CARREGADO')")) {
                for (int r=0;r<m.getRowCount();r++) {
                    int idPed = Integer.parseInt(String.valueOf(m.getValueAt(r,1)));
                    int seqN  = (Integer) m.getValueAt(r,0);
                    ps.setInt(1, idPed); ps.setInt(2, idRota); ps.setInt(3, idVeiculo); ps.setInt(4, seqN); ps.addBatch();
                }
                ps.executeBatch();
            }
            info("Entregas geradas na rota " + idRota);
        } catch (Exception ex) { erro("Falha ao enviar para entrega: " + ex.getMessage()); }
    }

    // ---- util ----
    private void info(String msg){ JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE); }
    private void erro(String msg){ JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaRoteirizacao().setVisible(true));
    }
}
