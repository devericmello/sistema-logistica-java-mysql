package GUI;

import DAO.PedidoDAO;
import Modelo.Pedido;
import Modelo.PedidoItem;
import Factory.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

/**
 * Gestão de Pedidos — ciclo: criar, itens, separar, expedir, ocorrências, entrega, KPIs.
 * Janela grande e colunas autoajustadas.
 */
public class TelaGestaoPedidos extends JFrame {

    // UI
    private JPanel pnlRoot, pnlFiltro;
    private JTextField txtBuscaCliente;
    private JComboBox<String> cbStatusFiltro;
    private JButton btnNovoPedido, btnAtualizar;

    private JSplitPane splitMain;
    private JTable tblPedidos, tblItens;
    private JTabbedPane tabsDetalhe;

    // Itens
    private JButton btnAdicionarItem, btnRemoverItem, btnSeparar, btnExpedir, btnCancelar, btnConfirmarEntrega;

    // Ocorrências
    private JTextArea txtOcorrenciaDescr;
    private JComboBox<String> cbOcorrenciaTipo, cbOcorrenciaGravidade;
    private JButton btnRegistrarOcorrencia;

    // Entrega
    private JComboBox<String> cbVeiculo;
    private JTextField txtSequenciaEntrega;
    private JButton btnAlocarRota, btnMarcarEmTransito, btnMarcarEntregue;

    // KPIs
    private JLabel lblNivelServico, lblGiro, lblAcuracidade;
    private JButton btnRecarregarKPIs;

    // Estado/DAO
    private final Connection conn;
    private final PedidoDAO pedidoDAO;
    private Integer pedidoSelecionadoId = null;
    private Integer estoqueOrigemSelecionado = null;

    /** Usa a Connection do ConnectionFactory. */
    public TelaGestaoPedidos() {
        this(ConnectionFactory.getConnection());
    }

    public TelaGestaoPedidos(Connection conn) {
        super("Gestão de Pedidos — Treino");
        this.conn = conn;
        this.pedidoDAO = new PedidoDAO(conn);
        montarUI();
        wireEvents();
        carregarPedidos();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Janela maior
        setSize(1360, 820);
        setMinimumSize(new Dimension(1200, 720));
        setLocationRelativeTo(null);
    }

    private void montarUI() {
        pnlRoot = new JPanel(new BorderLayout(8,8));
        setContentPane(pnlRoot);

        // Barra de filtro
        pnlFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscaCliente = new JTextField(24);
        cbStatusFiltro = new JComboBox<>(new String[]{
                "TODOS","ABERTO","SEPARACAO","EXPEDIDO","EM_TRANSITO","ENTREGUE","CANCELADO"
        });
        btnAtualizar  = new JButton("Atualizar");
        btnNovoPedido = new JButton("Novo pedido");

        pnlFiltro.add(new JLabel("Cliente:"));
        pnlFiltro.add(txtBuscaCliente);
        pnlFiltro.add(new JLabel("Status:"));
        pnlFiltro.add(cbStatusFiltro);
        pnlFiltro.add(btnAtualizar);
        pnlFiltro.add(btnNovoPedido);
        pnlRoot.add(pnlFiltro, BorderLayout.NORTH);

        // Split esquerda/direita
        splitMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitMain.setResizeWeight(0.48); // dá mais espaço à lista de pedidos

        // Tabela de pedidos
        tblPedidos = new JTable(new DefaultTableModel(new Object[]{
                "ID","Cliente","Estoque Origem","Criado em","Prometida","Status","Canal","Observação"
        },0){
            public boolean isCellEditable(int r,int c){return false;}
            public Class<?> getColumnClass(int col){
                return switch (col){
                    case 0,2 -> Integer.class;
                    default -> String.class;
                };
            }
        });
        tblPedidos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // deixamos manual para autoajuste customizado
        splitMain.setLeftComponent(new JScrollPane(tblPedidos));

        // Abas de detalhe
        tabsDetalhe = new JTabbedPane();

        // Aba Itens
        JPanel pnlItens = new JPanel(new BorderLayout(4,4));
        tblItens = new JTable(new DefaultTableModel(new Object[]{
                "ID Item","Material","Qtd","Status","Reservado"
        },0){ public boolean isCellEditable(int r,int c){return false;}});
        pnlItens.add(new JScrollPane(tblItens), BorderLayout.CENTER);

        JPanel pnlItensAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdicionarItem = new JButton("Adicionar item");
        btnRemoverItem   = new JButton("Remover item");
        btnSeparar       = new JButton("Separar");
        btnExpedir       = new JButton("Expedir");
        btnCancelar      = new JButton("Cancelar pedido");
        btnConfirmarEntrega = new JButton("Confirmar entrega");
        pnlItensAcoes.add(btnAdicionarItem);
        pnlItensAcoes.add(btnRemoverItem);
        pnlItensAcoes.add(btnSeparar);
        pnlItensAcoes.add(btnExpedir);
        pnlItensAcoes.add(btnCancelar);
        pnlItensAcoes.add(btnConfirmarEntrega);
        pnlItens.add(pnlItensAcoes, BorderLayout.SOUTH);
        tabsDetalhe.add("Itens", pnlItens);

        // Aba Ocorrências
        JPanel pnlOc = new JPanel(new BorderLayout(4,4));
        txtOcorrenciaDescr = new JTextArea(6, 30);
        cbOcorrenciaTipo = new JComboBox<>(new String[]{
                "ATRASO","PRODUTO_ERRADO","AVARIA","ENDERECO_INCORRETO","CLIENTE_AUSENTE","OUTROS"
        });
        cbOcorrenciaGravidade = new JComboBox<>(new String[]{"BAIXA","MEDIA","ALTA"});
        btnRegistrarOcorrencia = new JButton("Registrar ocorrência");
        JPanel barraOc = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barraOc.add(new JLabel("Tipo:")); barraOc.add(cbOcorrenciaTipo);
        barraOc.add(new JLabel("Gravidade:")); barraOc.add(cbOcorrenciaGravidade);
        barraOc.add(btnRegistrarOcorrencia);
        pnlOc.add(barraOc, BorderLayout.NORTH);
        pnlOc.add(new JScrollPane(txtOcorrenciaDescr), BorderLayout.CENTER);
        tabsDetalhe.add("Ocorrências", pnlOc);

        // Aba Entrega
        JPanel pnlEnt = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbVeiculo = new JComboBox<>();
        txtSequenciaEntrega = new JTextField(6);
        btnAlocarRota = new JButton("Alocar em rota");
        btnMarcarEmTransito = new JButton("Marcar em trânsito");
        btnMarcarEntregue   = new JButton("Marcar entregue");
        pnlEnt.add(new JLabel("Veículo:")); pnlEnt.add(cbVeiculo);
        pnlEnt.add(new JLabel("Seq.:"));    pnlEnt.add(txtSequenciaEntrega);
        pnlEnt.add(btnAlocarRota);
        pnlEnt.add(btnMarcarEmTransito);
        pnlEnt.add(btnMarcarEntregue);
        tabsDetalhe.add("Entrega", pnlEnt);

        // Aba KPIs
        JPanel pnlKpi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblNivelServico = new JLabel("Nível de serviço: -- %");
        lblGiro         = new JLabel("Giro: --");
        lblAcuracidade  = new JLabel("Acuracidade: -- %");
        btnRecarregarKPIs = new JButton("Recarregar KPIs");
        pnlKpi.add(lblNivelServico); pnlKpi.add(Box.createHorizontalStrut(16));
        pnlKpi.add(lblGiro);         pnlKpi.add(Box.createHorizontalStrut(16));
        pnlKpi.add(lblAcuracidade);  pnlKpi.add(Box.createHorizontalStrut(16));
        pnlKpi.add(btnRecarregarKPIs);
        tabsDetalhe.add("KPIs", pnlKpi);

        splitMain.setRightComponent(tabsDetalhe);
        pnlRoot.add(splitMain, BorderLayout.CENTER);
    }

    private void wireEvents() {
        btnAtualizar.addActionListener(e -> carregarPedidos());
        cbStatusFiltro.addActionListener(e -> carregarPedidos());
        tblPedidos.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) onSelecionouPedido();
        });

        btnNovoPedido.addActionListener(e -> onNovoPedido());
        btnAdicionarItem.addActionListener(e -> onAdicionarItem());
        btnRemoverItem.addActionListener(e -> onRemoverItem());
        btnSeparar.addActionListener(e -> onSeparar());
        btnExpedir.addActionListener(e -> onExpedir());
        btnCancelar.addActionListener(e -> onCancelarPedido());
        btnConfirmarEntrega.addActionListener(e -> onConfirmarEntrega());

        btnRegistrarOcorrencia.addActionListener(e -> onRegistrarOcorrencia());
        btnRecarregarKPIs.addActionListener(e -> carregarKPIs());
        btnAlocarRota.addActionListener(e -> onAlocarRota());
        btnMarcarEmTransito.addActionListener(e -> onMarcarEmTransito());
        btnMarcarEntregue.addActionListener(e -> onConfirmarEntrega());

        // Sempre que a janela for exibida ou redimensionada, reajusta as colunas
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e){ ajustarLargurasTabela(tblPedidos, 80); }
            public void componentResized(java.awt.event.ComponentEvent e){ ajustarLargurasTabela(tblPedidos, 80); }
        });
    }

    private void carregarPedidos() {
        try {
            String status = (String) cbStatusFiltro.getSelectedItem();
            String termo  = txtBuscaCliente.getText();
            List<Pedido> pedidos = pedidoDAO.listarPedidos(status, termo);

            DefaultTableModel m = (DefaultTableModel) tblPedidos.getModel();
            m.setRowCount(0);
            for (Pedido p : pedidos) {
                m.addRow(new Object[]{
                        p.getIdPedido(), p.getNomeCliente(), p.getIdEstoqueOrigem(),
                        p.getDataCriacao(), p.getDataPrometida(), p.getStatus(), p.getCanal(), p.getObservacao()
                });
            }
            pedidoSelecionadoId = null;
            estoqueOrigemSelecionado = null;
            ((DefaultTableModel)tblItens.getModel()).setRowCount(0);
            carregarKPIs();
            carregarVeiculosCombo();

            ajustarLargurasTabela(tblPedidos, 80); // aplica autoajuste após recarregar
        } catch (Exception ex) { erro("Falha ao carregar pedidos: " + ex.getMessage()); }
    }

    private void onSelecionouPedido() {
        int row = tblPedidos.getSelectedRow();
        if (row < 0) {
            pedidoSelecionadoId = null; estoqueOrigemSelecionado = null;
            ((DefaultTableModel)tblItens.getModel()).setRowCount(0); return;
        }
        pedidoSelecionadoId = (Integer) tblPedidos.getValueAt(row, 0);
        estoqueOrigemSelecionado = (Integer) tblPedidos.getValueAt(row, 2);
        carregarItensPedido(pedidoSelecionadoId);
    }

    private void carregarItensPedido(int idPedido) {
        try {
            List<PedidoItem> itens = pedidoDAO.listarItens(idPedido);
            DefaultTableModel m = (DefaultTableModel) tblItens.getModel();
            m.setRowCount(0);
            for (PedidoItem it : itens) {
                m.addRow(new Object[]{ it.getIdPedidoItem(), it.getNomeMaterial(), it.getQuantidade(),
                        it.getStatus(), it.getReservadoEstoque() });
            }
        } catch (Exception ex) { erro("Falha ao carregar itens: " + ex.getMessage()); }
    }

    private void onNovoPedido() {
        try {
            String sIdCli = JOptionPane.showInputDialog(this, "ID do cliente:");
            String sIdEst = JOptionPane.showInputDialog(this, "ID do estoque origem:");
            if (sIdCli == null || sIdEst == null) return;
            Pedido p = new Pedido();
            p.setIdCliente(Integer.parseInt(sIdCli.trim()));
            p.setIdEstoqueOrigem(Integer.parseInt(sIdEst.trim()));
            p.setCanal("B2C");
            p.setStatus("ABERTO");
            int novoId = pedidoDAO.criarPedido(p);
            info("Pedido criado: " + novoId);
            carregarPedidos();
            selecionarPedidoNaTabela(novoId);
        } catch (Exception ex) { erro("Falha ao criar pedido: " + ex.getMessage()); }
    }

    private void selecionarPedidoNaTabela(int id) {
        DefaultTableModel m = (DefaultTableModel) tblPedidos.getModel();
        for (int i=0;i<m.getRowCount();i++) {
            if (((Integer)m.getValueAt(i,0)) == id) {
                tblPedidos.setRowSelectionInterval(i,i);
                tblPedidos.scrollRectToVisible(tblPedidos.getCellRect(i,0,true));
                break;
            }
        }
    }

    private void onAdicionarItem() {
        if (pedidoSelecionadoId == null) { info("Selecione um pedido."); return; }
        String sIdMat = JOptionPane.showInputDialog(this, "ID do material:");
        String sQtd   = JOptionPane.showInputDialog(this, "Quantidade:");
        if (sIdMat == null || sQtd == null) return;
        try {
            pedidoDAO.adicionarItem(pedidoSelecionadoId, Integer.parseInt(sIdMat.trim()), Integer.parseInt(sQtd.trim()));
            carregarItensPedido(pedidoSelecionadoId);
        } catch (Exception ex) { erro("Falha ao adicionar item: " + ex.getMessage()); }
    }

    private void onRemoverItem() { info("Remoção direta não implementada. Cancele o pedido ou ajuste via banco."); }

    private void onSeparar() {
        if (pedidoSelecionadoId == null) { info("Selecione um pedido."); return; }
        int row = tblItens.getSelectedRow();
        if (row < 0) { info("Selecione um item."); return; }
        int idItem = (Integer) tblItens.getValueAt(row, 0);
        try { pedidoDAO.atualizarStatusItem(idItem, "SEPARADO"); carregarItensPedido(pedidoSelecionadoId); }
        catch (Exception ex) { erro("Falha ao separar item: " + ex.getMessage()); }
    }

    private void onExpedir() {
        if (pedidoSelecionadoId == null) { info("Selecione um pedido."); return; }
        if (estoqueOrigemSelecionado == null) { info("Estoque origem indefinido."); return; }
        int row = tblItens.getSelectedRow();
        if (row < 0) { info("Selecione um item."); return; }
        int idItem = (Integer) tblItens.getValueAt(row, 0);
        String nomeMat = String.valueOf(tblItens.getValueAt(row,1));
        int qtd = (Integer) tblItens.getValueAt(row,2);
        String sIdMat = JOptionPane.showInputDialog(this, "Confirme o ID do material ("+nomeMat+"): ");
        if (sIdMat == null) return;
        try {
            pedidoDAO.expedirItem(estoqueOrigemSelecionado, Integer.parseInt(sIdMat.trim()), qtd, idItem);
            pedidoDAO.atualizarStatus(pedidoSelecionadoId, "EXPEDIDO");
            carregarItensPedido(pedidoSelecionadoId); carregarPedidos();
        } catch (Exception ex) { erro("Falha ao expedir: " + ex.getMessage()); }
    }

    private void onCancelarPedido() {
        if (pedidoSelecionadoId == null) { info("Selecione um pedido."); return; }
        try { pedidoDAO.atualizarStatus(pedidoSelecionadoId, "CANCELADO"); carregarPedidos(); }
        catch (Exception ex) { erro("Falha ao cancelar: " + ex.getMessage()); }
    }

    private void onConfirmarEntrega() {
        if (pedidoSelecionadoId == null) { info("Selecione um pedido."); return; }
        try { pedidoDAO.confirmarEntrega(pedidoSelecionadoId, null); carregarPedidos(); }
        catch (Exception ex) { erro("Falha ao confirmar entrega: " + ex.getMessage()); }
    }

    private void onRegistrarOcorrencia() {
        if (pedidoSelecionadoId == null) { info("Selecione um pedido."); return; }
        String tipo = (String) cbOcorrenciaTipo.getSelectedItem();
        String grav = (String) cbOcorrenciaGravidade.getSelectedItem();
        String desc = txtOcorrenciaDescr.getText();
        if (desc == null || desc.isBlank()) { info("Descreva a ocorrência."); return; }
        try { pedidoDAO.registrarOcorrencia(pedidoSelecionadoId, tipo, desc, grav); info("Ocorrência registrada."); txtOcorrenciaDescr.setText(""); }
        catch (Exception ex) { erro("Falha ao registrar ocorrência: " + ex.getMessage()); }
    }

    private void carregarKPIs() {
        try {
            double ns = pedidoDAO.nivelServicoDoDia(LocalDate.now());
            lblNivelServico.setText(String.format("Nível de serviço: %.2f %%", ns));
            lblGiro.setText("Giro: vw_giro_estoque_mensal");
            lblAcuracidade.setText("Acuracidade: vw_acuracidade_inventario");
        } catch (Exception ignore) {}
    }

    private void carregarVeiculosCombo() {
        try (var st = conn.createStatement();
             var rs = st.executeQuery("SELECT id_veiculo, placa FROM veiculo ORDER BY id_veiculo")) {
            cbVeiculo.removeAllItems();
            while (rs.next()) cbVeiculo.addItem(rs.getInt(1) + " - " + rs.getString(2));
        } catch (Exception ignore) {}
    }

    private int criarOuObterRotaHoje(Connection c, int idEstoque) throws Exception {
        String hoje = java.time.LocalDate.now().toString();
        try (var ps = c.prepareStatement("SELECT id_rota FROM rota WHERE data_rota=? AND id_estoque_origem=?")) {
            ps.setString(1, hoje); ps.setInt(2, idEstoque);
            try (var rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        }
        try (var ps = c.prepareStatement(
                "INSERT INTO rota (data_rota,id_estoque_origem,objetivo,algoritmo,criado_por) VALUES (?,?, 'MENOR_TEMPO','MANUAL','tela')",
                java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, hoje); ps.setInt(2, idEstoque); ps.executeUpdate();
            try (var rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
        }
        throw new IllegalStateException("Não foi possível criar rota.");
    }

    private void onAlocarRota() {
        if (pedidoSelecionadoId == null) { info("Selecione um pedido."); return; }
        String veic = (String) cbVeiculo.getSelectedItem();
        if (veic == null) { info("Cadastre veículo na tabela 'veiculo'."); return; }
        String sSeq = txtSequenciaEntrega.getText().isBlank() ? "1" : txtSequenciaEntrega.getText();
        int idVeiculo = Integer.parseInt(veic.split(" - ")[0]);
        try {
            int idRota = criarOuObterRotaHoje(conn, estoqueOrigemSelecionado);
            try (var ps = conn.prepareStatement(
                    "INSERT INTO entrega (id_pedido,id_rota,id_veiculo,sequencia,status) VALUES (?,?,?,?, 'CARREGADO')")) {
                ps.setInt(1, pedidoSelecionadoId);
                ps.setInt(2, idRota);
                ps.setInt(3, idVeiculo);
                ps.setInt(4, Integer.parseInt(sSeq));
                ps.executeUpdate();
            }
            pedidoDAO.atualizarStatus(pedidoSelecionadoId, "EXPEDIDO");
            info("Pedido alocado na rota " + idRota + ". Status: CARREGADO");
        } catch (Exception ex) { erro("Falha ao alocar rota: " + ex.getMessage()); }
    }

    private void onMarcarEmTransito() {
        if (pedidoSelecionadoId == null) { info("Selecione um pedido."); return; }
        try (var ps = conn.prepareStatement("UPDATE entrega SET status='EM_TRANSITO' WHERE id_pedido=?")) {
            ps.setInt(1, pedidoSelecionadoId); ps.executeUpdate();
        } catch (Exception ex) { erro("Falha ao marcar em trânsito: " + ex.getMessage()); return; }
        try { pedidoDAO.atualizarStatus(pedidoSelecionadoId, "EM_TRANSITO"); carregarPedidos(); }
        catch (Exception ex) { erro("Falha ao atualizar status do pedido: " + ex.getMessage()); }
    }

    // ---------- Utilidades UI ----------

    /** Ajusta larguras das colunas baseado no conteúdo visível. */
    private void ajustarLargurasTabela(JTable table, int larguraMinPorColuna) {
        TableColumnModel colModel = table.getColumnModel();
        FontMetrics fm = table.getFontMetrics(table.getFont());

        for (int col = 0; col < table.getColumnCount(); col++) {
            int largura = larguraMinPorColuna;

            // Header
            String header = table.getColumnName(col);
            largura = Math.max(largura, fm.stringWidth(header) + 24);

            // Amostra de linhas (todas — simples e didático)
            for (int row = 0; row < table.getRowCount(); row++) {
                Object v = table.getValueAt(row, col);
                String s = v == null ? "" : String.valueOf(v);
                largura = Math.max(largura, fm.stringWidth(s) + 24);
            }

            TableColumn c = colModel.getColumn(col);
            c.setPreferredWidth(Math.min(largura, 380)); // teto para não exagerar
        }

        // Se couber, usa AUTO_RESIZE_SUBSEQUENT; se não, deixa barras de rolagem
        int total = 0;
        for (int i=0;i<colModel.getColumnCount();i++) total += colModel.getColumn(i).getPreferredWidth();
        if (total < table.getParent().getWidth()) {
            table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        } else {
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
        table.revalidate();
    }

    private void info(String msg){ JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE); }
    private void erro(String msg){ JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaGestaoPedidos().setVisible(true));
    }
}
