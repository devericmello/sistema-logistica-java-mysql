package GUI;

import Modelo.Questao;
import Modelo.Alternativa;
import DAO.QuestaoDAO;
import DAO.AlternativaDAO;
import Modelo.Instrutor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.io.File;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CadastrarProva extends JFrame {

    // ===== contexto =====
    private final Connection conn;
    private final int id_aluno;
    private final String nome_completo;
    private final Instrutor instrutor;

    // ===== UI =====
    private JTextField txtDescricao;
    private JFormattedTextField txtDataAplicacao; // máscara yyyy-MM-dd
    private JButton btnAdicionarQuestao, btnRemoverQuestao, btnSalvar, btnCancelar;
    private JTable tabelaQuestoes;
    private DefaultTableModel modeloQuestoes;
    private JLabel lblTotal;

    // ===== dados temporários =====
    private final List<Questao> listaQuestoes = new ArrayList<>();
    private final Map<Questao, List<Alternativa>> alternativasPorQuestao = new HashMap<>();

    // ===== construtores =====
    public CadastrarProva(Connection conn, int id_aluno, String nome_completo, Instrutor instrutor) {
        this.conn = conn;
        this.id_aluno = id_aluno;
        this.nome_completo = nome_completo;
        this.instrutor = instrutor;

        setTitle("Adicionar Prova");
        setSize(1000, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { voltarAoMenuComConfirmacao(); }
        });

        montarUI();
        configurarAtalhos();
    }

    // overload p/ testes
    public CadastrarProva(Connection conn) { this(conn, 0, "", null); }

    // ===== UI =====
    private void montarUI() {
        // topo
        JPanel panelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTopo.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField(28);
        panelTopo.add(txtDescricao);

        panelTopo.add(new JLabel("Data (YYYY-MM-DD):"));
        txtDataAplicacao = criarCampoDataISO();
        txtDataAplicacao.setColumns(10);
        panelTopo.add(txtDataAplicacao);

        // tabela
        modeloQuestoes = new DefaultTableModel(new Object[]{"Nº", "Enunciado", "Alternativas", "Correta", "Img"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
            @Override public Class<?> getColumnClass(int columnIndex) { return String.class; }
        };
        tabelaQuestoes = new JTable(modeloQuestoes) {
            @Override public Dimension getPreferredScrollableViewportSize() {
                Dimension d = super.getPreferredScrollableViewportSize();
                d.height = Math.max(d.height, 380);
                return d;
            }
        };
        tabelaQuestoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaQuestoes.getTableHeader().setReorderingAllowed(false);
        tabelaQuestoes.setAutoCreateRowSorter(true);
        tabelaQuestoes.setRowSorter(new TableRowSorter<>(modeloQuestoes));
        JScrollPane scrollTable = new JScrollPane(tabelaQuestoes);

        // larguras
        tabelaQuestoes.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelaQuestoes.getColumnModel().getColumn(1).setPreferredWidth(420);
        tabelaQuestoes.getColumnModel().getColumn(2).setPreferredWidth(360);
        tabelaQuestoes.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabelaQuestoes.getColumnModel().getColumn(4).setPreferredWidth(40);

        // renderer HTML para quebrar linha nas colunas 1 e 2
        HtmlWrapRenderer wrap420 = new HtmlWrapRenderer(420);
        HtmlWrapRenderer wrap360 = new HtmlWrapRenderer(360);
        tabelaQuestoes.getColumnModel().getColumn(1).setCellRenderer(wrap420);
        tabelaQuestoes.getColumnModel().getColumn(2).setCellRenderer(wrap360);

        // lateral
        JPanel panelBotoesQuestoes = new JPanel(new GridLayout(3, 1, 8, 8));
        btnAdicionarQuestao = new JButton("Adicionar Questão  (Ctrl+N)");
        btnRemoverQuestao  = new JButton("Remover Selecionada (Del)");
        lblTotal = new JLabel("Total: 0");
        panelBotoesQuestoes.add(btnAdicionarQuestao);
        panelBotoesQuestoes.add(btnRemoverQuestao);
        panelBotoesQuestoes.add(lblTotal);

        // rodapé
        JPanel panelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnSalvar = new JButton("Salvar Prova (Ctrl+S)");
        btnCancelar = new JButton("Cancelar (Esc)");
        panelRodape.add(btnSalvar);
        panelRodape.add(btnCancelar);

        // layout
        setLayout(new BorderLayout(10, 10));
        add(panelTopo, BorderLayout.NORTH);
        add(scrollTable, BorderLayout.CENTER);
        add(panelBotoesQuestoes, BorderLayout.WEST);
        add(panelRodape, BorderLayout.SOUTH);

        // ações
        btnAdicionarQuestao.addActionListener(e -> abrirDialogoAdicionarQuestao());
        btnRemoverQuestao.addActionListener(e -> removerQuestaoSelecionada());
        btnSalvar.addActionListener(e -> salvarProva());
        btnCancelar.addActionListener(e -> voltarAoMenuComConfirmacao());
    }

    private JFormattedTextField criarCampoDataISO() {
        try {
            MaskFormatter mf = new MaskFormatter("####-##-##"); // YYYY-MM-DD
            mf.setPlaceholderCharacter('_');
            return new JFormattedTextField(mf);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    private void configurarAtalhos() {
        JComponent root = getRootPane();

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "novo");
        root.getActionMap().put("novo", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { abrirDialogoAdicionarQuestao(); }
        });

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "salvar");
        root.getActionMap().put("salvar", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { salvarProva(); }
        });

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "remover");
        root.getActionMap().put("remover", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { removerQuestaoSelecionada(); }
        });

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelar");
        root.getActionMap().put("cancelar", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { voltarAoMenuComConfirmacao(); }
        });
    }

    private void atualizarTotal() {
        lblTotal.setText("Total: " + modeloQuestoes.getRowCount());
    }

    // ===== ações =====
    private void removerQuestaoSelecionada() {
        int selected = tabelaQuestoes.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma questão.");
            return;
        }
        int modelRow = tabelaQuestoes.convertRowIndexToModel(selected);
        Questao q = listaQuestoes.get(modelRow);
        listaQuestoes.remove(modelRow);
        alternativasPorQuestao.remove(q);
        modeloQuestoes.removeRow(modelRow);
        atualizarTotal();
    }

    private void abrirDialogoAdicionarQuestao() {
        JTextField txtNumero = new JTextField();

        JTextArea taEnunciado = new JTextArea(6, 40);
        taEnunciado.setLineWrap(true);
        taEnunciado.setWrapStyleWord(true);

        // --- imagem (opcional) ---
        JLabel lblPreview = new JLabel("Sem imagem", SwingConstants.CENTER);
        lblPreview.setPreferredSize(new Dimension(240, 160));
        lblPreview.setBorder(BorderFactory.createTitledBorder("Imagem (opcional)"));
        final byte[][] imagemBytesHolder = new byte[1][]; // captura no OK

        JButton btnAnexar = new JButton("Anexar imagem...");
        JButton btnRemoverImg = new JButton("Remover");
        btnAnexar.addActionListener(ev -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Imagens (PNG, JPG, JPEG, GIF)", "png","jpg","jpeg","gif"));
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                try {
                    imagemBytesHolder[0] = Files.readAllBytes(f.toPath()); // guarda bytes
                    BufferedImage bi = ImageIO.read(f);                   // preview
                    lblPreview.setIcon(scaleIcon(bi, 240, 160));
                    lblPreview.setText(null);
                } catch (Exception ex) {
                    imagemBytesHolder[0] = null;
                    lblPreview.setIcon(null);
                    lblPreview.setText("Erro ao ler imagem");
                    JOptionPane.showMessageDialog(this, "Não foi possível carregar a imagem: " + ex.getMessage());
                }
            }
        });
        btnRemoverImg.addActionListener(ev -> {
            imagemBytesHolder[0] = null;
            lblPreview.setIcon(null);
            lblPreview.setText("Sem imagem");
        });

        JPanel painelImagemBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        painelImagemBtns.add(btnAnexar);
        painelImagemBtns.add(btnRemoverImg);

        JPanel painelImagem = new JPanel(new BorderLayout(6, 6));
        painelImagem.add(lblPreview, BorderLayout.CENTER);
        painelImagem.add(painelImagemBtns, BorderLayout.SOUTH);

        JPanel painelQuestao = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4,4,4,4);

        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.WEST;
        painelQuestao.add(new JLabel("Número da Questão:"), gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL; gc.weightx = 1;
        painelQuestao.add(txtNumero, gc);

        gc.gridx = 0; gc.gridy = 1; gc.fill = GridBagConstraints.NONE; gc.weightx = 0;
        painelQuestao.add(new JLabel("Enunciado da Questão:"), gc);
        gc.gridx = 1; gc.gridy = 1; gc.fill = GridBagConstraints.BOTH; gc.weightx = 1; gc.weighty = 1;
        JScrollPane spEnun = new JScrollPane(taEnunciado);
        spEnun.setPreferredSize(new Dimension(520, 140));
        painelQuestao.add(spEnun, gc);

        // imagem ao lado do enunciado
        gc.gridx = 2; gc.gridy = 1; gc.gridheight = 2; gc.weightx = 0; gc.weighty = 0; gc.fill = GridBagConstraints.NONE; gc.anchor = GridBagConstraints.NORTH;
        painelQuestao.add(painelImagem, gc);
        gc.gridheight = 1;

        List<JTextArea> txtAlternativas = new ArrayList<>();
        ButtonGroup grupo = new ButtonGroup();
        List<JRadioButton> radios = new ArrayList<>();

        gc.gridx = 0; gc.gridy = 2; gc.gridwidth = 2; gc.fill = GridBagConstraints.NONE; gc.weighty = 0; gc.anchor = GridBagConstraints.WEST;
        painelQuestao.add(new JLabel("Alternativas (marque 1 correta):"), gc);

        JPanel painelAlternativas = new JPanel(new GridLayout(5, 1, 6, 6));
        for (char letra = 'A'; letra <= 'E'; letra++) {
            JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
            JRadioButton radio = new JRadioButton();
            grupo.add(radio);
            radios.add(radio);
            JTextArea txtAlt = new JTextArea(2, 40);
            txtAlt.setLineWrap(true);
            txtAlt.setWrapStyleWord(true);
            txtAlternativas.add(txtAlt);
            altPanel.add(new JLabel(letra + ":"));
            altPanel.add(new JScrollPane(txtAlt));
            altPanel.add(new JLabel("Correta"));
            altPanel.add(radio);
            painelAlternativas.add(altPanel);
        }
        gc.gridy = 3; gc.fill = GridBagConstraints.HORIZONTAL;
        painelQuestao.add(painelAlternativas, gc);

        int res = JOptionPane.showConfirmDialog(this, painelQuestao, "Adicionar Questão",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res != JOptionPane.OK_OPTION) return;

        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            String enunciado = taEnunciado.getText().trim();

            List<Alternativa> alternativas = new ArrayList<>();
            int corretas = 0;
            for (int i = 0; i < txtAlternativas.size(); i++) {
                String textoAlt = txtAlternativas.get(i).getText().trim();
                if (!textoAlt.isEmpty()) {
                    Alternativa alt = new Alternativa();
                    alt.setLetra((char) ('A' + i));
                    alt.setTexto(textoAlt);
                    boolean isCorreta = radios.get(i).isSelected();
                    alt.setCorreta(isCorreta);
                    if (isCorreta) corretas++;
                    alternativas.add(alt);
                }
            }

            if (enunciado.isEmpty() || alternativas.size() < 2 || corretas != 1) {
                JOptionPane.showMessageDialog(this,
                        "Preencha enunciado, pelo menos 2 alternativas e marque exatamente 1 correta.");
                return;
            }

            Questao questao = new Questao();
            questao.setNumeroQuestao(numero);
            questao.setEnunciado(enunciado);
            questao.setImagem(imagemBytesHolder[0]); // <<< imagem (pode ser null)

            listaQuestoes.add(questao);
            alternativasPorQuestao.put(questao, alternativas);

            String htmlEnun = toHtmlWrapped(enunciado, 420);
            String htmlAlts = toHtmlWrapped(
                    alternativas.stream().map(a -> a.getLetra() + ") " + a.getTexto())
                            .collect(Collectors.joining(" | ")), 360);

            modeloQuestoes.addRow(new Object[]{
                    String.valueOf(numero),
                    htmlEnun,
                    htmlAlts,
                    alternativas.stream().filter(Alternativa::isCorreta).map(a -> "" + a.getLetra()).findFirst().orElse(""),
                    (imagemBytesHolder[0] != null ? "📷" : "")
            });

            int last = modeloQuestoes.getRowCount() - 1;
            setTooltip(last, 1, enunciado);
            setTooltip(last, 2, alternativas.stream().map(a -> a.getLetra() + ") " + a.getTexto()).collect(Collectors.joining(" | ")));

            tabelaQuestoes.setRowHeight(last, 64);
            atualizarTotal();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número da questão inválido.");
        }
    }

    private ImageIcon scaleIcon(BufferedImage img, int w, int h) {
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private void setTooltip(int modelRow, int col, String text) {
        int viewRow = tabelaQuestoes.convertRowIndexToView(modelRow);
        tabelaQuestoes.getColumnModel().getColumn(col).setCellRenderer(new HtmlWrapRenderer(
                col == 1 ? 420 : 360, text));
    }

    private static String toHtmlWrapped(String s, int widthPx) {
        String safe = s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                       .replace("\n", "<br>");
        return "<html><div style='width:" + widthPx + "px;'>" + safe + "</div></html>";
    }

    private void salvarProva() {
        String descricao = txtDescricao.getText().trim();
        String dataStr = txtDataAplicacao.getText().trim();

        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a descrição.");
            return;
        }
        try { LocalDate.parse(dataStr); }
        catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato YYYY-MM-DD.");
            return;
        }
        if (listaQuestoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione ao menos uma questão.");
            return;
        }

        try {
            Collections.shuffle(listaQuestoes);
            for (Questao q : listaQuestoes) Collections.shuffle(alternativasPorQuestao.get(q));

            boolean oldAuto = conn.getAutoCommit();
            conn.setAutoCommit(false);

            int idProva = inserirProvaNoBanco(descricao, dataStr);

            QuestaoDAO questaoDAO = new QuestaoDAO(conn);
            AlternativaDAO alternativaDAO = new AlternativaDAO(conn);

            for (Questao q : listaQuestoes) {
                q.setIdProva(idProva);
                questaoDAO.inserir(q); // agora grava a imagem também
                List<Alternativa> alts = alternativasPorQuestao.get(q);
                for (Alternativa alt : alts) {
                    alt.setIdQuestao(q.getIdQuestao());
                    alternativaDAO.inserir(alt);
                }
            }

            // cria linhas de resposta para todos alunos
            List<Integer> alunos = new ArrayList<>();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT id_aluno FROM aluno")) {
                while (rs.next()) alunos.add(rs.getInt("id_aluno"));
            }

            String sqlResposta = "INSERT INTO resposta_aluno (id_aluno, id_questao, id_alternativa_escolhida, data_resposta) VALUES (?, ?, NULL, NULL)";
            try (PreparedStatement ps = conn.prepareStatement(sqlResposta)) {
                for (int idAluno : alunos) {
                    for (Questao q : listaQuestoes) {
                        ps.setInt(1, idAluno);
                        ps.setInt(2, q.getIdQuestao());
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }

            conn.commit();
            conn.setAutoCommit(oldAuto);

            JOptionPane.showMessageDialog(this, "Prova salva com sucesso.");
            new Menu(conn, id_aluno, nome_completo, instrutor).setVisible(true);
            dispose();

        } catch (Exception ex) {
            try { conn.rollback(); } catch (Exception ignore) {}
            JOptionPane.showMessageDialog(this, "Erro ao salvar a prova: " + ex.getMessage());
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ignore) {}
        }
    }

    private int inserirProvaNoBanco(String descricao, String dataAplicacao) throws Exception {
        String sql = "INSERT INTO prova (descricao, data_aplicacao, id_instrutor) VALUES (?, ?, 1)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, descricao);
            stmt.setString(2, dataAplicacao);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new Exception("Erro ao obter ID da prova.");
    }

    private void voltarAoMenuComConfirmacao() {
        int op = JOptionPane.showConfirmDialog(this,
                "Cancelar e voltar ao menu? Alterações não salvas serão perdidas.",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            new Menu(conn, id_aluno, nome_completo, instrutor).setVisible(true);
            dispose();
        }
    }

    // ===== renderer HTML com largura fixa + tooltip opcional =====
    static class HtmlWrapRenderer extends DefaultTableCellRenderer {
        private final int widthPx;
        private final String fixedTooltip; // pode ser null

        HtmlWrapRenderer(int widthPx) { this(widthPx, null); }
        HtmlWrapRenderer(int widthPx, String tooltip) {
            this.widthPx = widthPx;
            this.fixedTooltip = tooltip;
            setVerticalAlignment(SwingConstants.TOP);
        }
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                boolean hasFocus, int row, int column) {
            String text = value == null ? "" : value.toString();
            super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
            setText(text);
            setToolTipText(fixedTooltip != null ? fixedTooltip : stripHtml(text));
            return this;
        }
        private String stripHtml(String html) {
            return html.replaceAll("(?i)<br\\s*/?>", "\n").replaceAll("<[^>]+>", "");
        }
    }

    // ===== main de teste =====
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/logistica", "root", ""
            );
            SwingUtilities.invokeLater(() -> new CadastrarProva(conn).setVisible(true));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco: " + ex.getMessage());
        }
    }
}
