package GUI;

import Modelo.Instrutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import javax.swing.table.TableCellEditor;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EditarProvaCompleto extends JFrame {
    private final Connection conn;
    private final int id_aluno;
    private final String nome_completo;
    private final Instrutor instrutor;

    private int idProvaSelecionada;

    private JTextField txtDescricao;
    private JFormattedTextField txtDataAplicacao;
    private JTable tabelaQuestoes;
    private DefaultTableModel modeloQuestoes;
    private JTable tabelaAlternativas;
    private DefaultTableModel modeloAlternativas;
    private JButton btnSalvar, btnFechar, btnRecarregar;
    private JButton btnAnexarImg, btnRemoverImg;   // <<< novos

    private final List<Integer> questoesIds = new ArrayList<>();
    private int questaoSelecionada = -1;

    public EditarProvaCompleto(Connection conn, int idProva, int id_aluno, String nome_completo, Instrutor instrutor) {
        this.conn = conn;
        this.idProvaSelecionada = idProva;
        this.id_aluno = id_aluno;
        this.nome_completo = nome_completo;
        this.instrutor = instrutor;

        setTitle("Editar Prova");
        setSize(980, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { voltarAoMenuComConfirmacao(); }
        });

        montarUI();
        configurarAtalhos();

        carregarProva();
        carregarQuestoes();
    }

    public EditarProvaCompleto(Connection conn, int idProva) { this(conn, idProva, 0, "", null); }

    private void montarUI() {
        JPanel panelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTopo.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField(28);
        panelTopo.add(txtDescricao);
        panelTopo.add(new JLabel("Data (YYYY-MM-DD):"));
        txtDataAplicacao = criarCampoDataISO();
        txtDataAplicacao.setColumns(10);
        panelTopo.add(txtDataAplicacao);

        modeloQuestoes = new DefaultTableModel(new Object[]{"ID", "Enunciado"}, 0) {
            @Override public boolean isCellEditable(int r, int c){ return c==1; }
            @Override public Class<?> getColumnClass(int c){ return String.class; }
        };
        tabelaQuestoes = new JTable(modeloQuestoes);
        tabelaQuestoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaQuestoes.getTableHeader().setReorderingAllowed(false);
        tabelaQuestoes.setAutoCreateRowSorter(true);
        tabelaQuestoes.setRowSorter(new TableRowSorter<>(modeloIsso(modeloQuestoes)));
        JScrollPane scrollQuestoes = new JScrollPane(tabelaQuestoes);
        tabelaQuestoes.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabelaQuestoes.getColumnModel().getColumn(1).setPreferredWidth(520);
        tabelaQuestoes.getColumnModel().getColumn(1).setCellRenderer(new HtmlWrapRenderer(520));
        tabelaQuestoes.getColumnModel().getColumn(1).setCellEditor(new TextAreaCellEditor(6, 40, 520));
        tabelaQuestoes.setRowHeight(64);

        modeloAlternativas = new DefaultTableModel(new Object[]{"ID","Letra","Texto","Correta"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return c==2 || c==3; }
            @Override public Class<?> getColumnClass(int c){ return c==3 ? Boolean.class : String.class; }
        };
        tabelaAlternativas = new JTable(modeloAlternativas);
        tabelaAlternativas.getTableHeader().setReorderingAllowed(false);
        tabelaAlternativas.setAutoCreateRowSorter(true);
        tabelaAlternativas.setRowSorter(new TableRowSorter<>(modeloIsso(modeloAlternativas)));
        JScrollPane scrollAlternativas = new JScrollPane(tabelaAlternativas);
        tabelaAlternativas.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabelaAlternativas.getColumnModel().getColumn(1).setPreferredWidth(60);
        tabelaAlternativas.getColumnModel().getColumn(2).setPreferredWidth(480);
        tabelaAlternativas.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabelaAlternativas.getColumnModel().getColumn(2).setCellRenderer(new HtmlWrapRenderer(480));
        tabelaAlternativas.getColumnModel().getColumn(2).setCellEditor(new TextAreaCellEditor(3, 40, 480));
        tabelaAlternativas.setRowHeight(48);

        btnSalvar = new JButton("Salvar Alterações (Ctrl+S)");
        btnRecarregar = new JButton("Recarregar (F5)");
        btnFechar = new JButton("Fechar (Esc)");
        btnAnexarImg = new JButton("Anexar/alterar imagem…"); // novos
        btnRemoverImg = new JButton("Remover imagem");

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotoes.add(btnAnexarImg);
        panelBotoes.add(btnRemoverImg);
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnRecarregar);
        panelBotoes.add(btnFechar);

        setLayout(new BorderLayout(10,10));
        add(panelTopo, BorderLayout.NORTH);
        JPanel panelMeio = new JPanel(new GridLayout(1,2,10,10));
        panelMeio.add(scrollQuestoes);
        panelMeio.add(scrollAlternativas);
        add(panelMeio, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);

        tabelaQuestoes.getSelectionModel().addListSelectionListener(e -> {
            int idx = tabelaQuestoes.getSelectedRow();
            if (!e.getValueIsAdjusting() && idx >= 0 && idx < tabelaQuestoes.getRowCount() && idx < questoesIds.size()) {
                int modelRow = tabelaQuestoes.convertRowIndexToModel(idx);
                questaoSelecionada = questoesIds.get(modelRow);
                carregarAlternativas(questaoSelecionada);
            }
        });

        btnSalvar.addActionListener(e -> salvarTudo());
        btnRecarregar.addActionListener(e -> {
            carregarProva();
            carregarQuestoes();
            modeloAlternativas.setRowCount(0);
            questaoSelecionada = -1;
        });
        btnFechar.addActionListener(e -> voltarAoMenuComConfirmacao());

        // anexar imagem
        btnAnexarImg.addActionListener(e -> {
            if (questaoSelecionada <= 0) { JOptionPane.showMessageDialog(this,"Selecione uma questão."); return; }
            JFileChooser ch = new JFileChooser();
            ch.setFileFilter(new FileNameExtensionFilter("Imagens (PNG/JPG/JPEG)", "png","jpg","jpeg"));
            if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = ch.getSelectedFile();
                try {
                    byte[] bytes = Files.readAllBytes(f.toPath());
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE questao SET imagem=?, imagem_path=NULL WHERE id_questao=?")) {
                        ps.setBytes(1, bytes);
                        ps.setInt(2, questaoSelecionada);
                        ps.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(this, "Imagem anexada.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Falha ao anexar: " + ex.getMessage());
                }
            }
        });

        // remover imagem
        btnRemoverImg.addActionListener(e -> {
            if (questaoSelecionada <= 0) { JOptionPane.showMessageDialog(this,"Selecione uma questão."); return; }
            int ok = JOptionPane.showConfirmDialog(this, "Remover a imagem desta questão?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (ok != JOptionPane.YES_OPTION) return;
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE questao SET imagem=NULL, imagem_path=NULL WHERE id_questao=?")) {
                ps.setInt(1, questaoSelecionada);
                ps.executeUpdate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Falha ao remover: " + ex.getMessage());
                return;
            }
            JOptionPane.showMessageDialog(this, "Imagem removida.");
        });
    }

    private static <T extends DefaultTableModel> T modeloIsso(T m){ return m; }

    private JFormattedTextField criarCampoDataISO() {
        try {
            MaskFormatter mf = new MaskFormatter("####-##-##");
            mf.setPlaceholderCharacter('_');
            return new JFormattedTextField(mf);
        } catch (ParseException e) { return new JFormattedTextField(); }
    }

    private void configurarAtalhos() {
        JComponent root = getRootPane();
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK),"salvar");
        root.getActionMap().put("salvar", new AbstractAction(){ @Override public void actionPerformed(java.awt.event.ActionEvent e){ salvarTudo(); }});

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),"recarregar");
        root.getActionMap().put("recarregar", new AbstractAction(){ @Override public void actionPerformed(java.awt.event.ActionEvent e){ btnRecarregar.doClick(); }});

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),"fechar");
        root.getActionMap().put("fechar", new AbstractAction(){ @Override public void actionPerformed(java.awt.event.ActionEvent e){ voltarAoMenuComConfirmacao(); }});
    }

    private void carregarProva() {
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT descricao, data_aplicacao FROM prova WHERE id_prova=?")) {
            st.setInt(1, idProvaSelecionada);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    txtDescricao.setText(rs.getString("descricao"));
                    txtDataAplicacao.setText(rs.getString("data_aplicacao"));
                } else JOptionPane.showMessageDialog(this, "Prova não encontrada.");
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao carregar prova: " + e.getMessage()); }
    }

    private void carregarQuestoes() {
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT id_questao, enunciado FROM questao WHERE id_prova=? ORDER BY id_questao")) {
            modeloQuestoes.setRowCount(0);
            questoesIds.clear();
            st.setInt(1, idProvaSelecionada);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int idQ = rs.getInt("id_questao");
                    String enun = rs.getString("enunciado");
                    questoesIds.add(idQ);
                    modeloQuestoes.addRow(new Object[]{ idQ, toHtmlWrapped(enun, 520) });
                }
            }
            if (modeloQuestoes.getRowCount() > 0) tabelaQuestoes.setRowSelectionInterval(0, 0);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao carregar questões: " + e.getMessage()); }
    }

    private void carregarAlternativas(int idQuestao) {
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT id_alternativa, letra, texto, correta FROM alternativa WHERE id_questao=? ORDER BY letra")) {
            modeloAlternativas.setRowCount(0);
            st.setInt(1, idQuestao);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    modeloAlternativas.addRow(new Object[]{
                            rs.getInt("id_alternativa"),
                            rs.getString("letra"),
                            toHtmlWrapped(rs.getString("texto"), 480),
                            rs.getBoolean("correta")
                    });
                }
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao carregar alternativas: " + e.getMessage()); }
    }

    private void salvarTudo() {
        String dataStr = txtDataAplicacao.getText().trim();
        try { LocalDate.parse(dataStr); }
        catch (DateTimeParseException ex) { JOptionPane.showMessageDialog(this,"Data inválida. Use o formato YYYY-MM-DD."); return; }

        if (questaoSelecionada != -1 && modeloAlternativas.getRowCount() > 0) {
            int corretas = 0;
            for (int i=0;i<modeloAlternativas.getRowCount();i++) {
                Boolean c = (Boolean) modeloAlternativas.getValueAt(i,3);
                if (c!=null && c) corretas++;
            }
            if (corretas != 1) { JOptionPane.showMessageDialog(this,"Marque exatamente UMA alternativa correta nesta questão."); return; }
        }

        try {
            boolean oldAuto = conn.getAutoCommit(); conn.setAutoCommit(false);

            try (PreparedStatement st = conn.prepareStatement(
                    "UPDATE prova SET descricao=?, data_aplicacao=? WHERE id_prova=?")) {
                st.setString(1, txtDescricao.getText().trim());
                st.setString(2, dataStr);
                st.setInt(3, idProvaSelecionada);
                st.executeUpdate();
            }

            for (int i=0;i<modeloQuestoes.getRowCount();i++) {
                int idQ = (int) modeloQuestoes.getValueAt(i,0);
                String enunHtml = (String) modeloQuestoes.getValueAt(i,1);
                String enun = stripHtml(enunHtml);
                try (PreparedStatement qs = conn.prepareStatement(
                        "UPDATE questao SET enunciado=? WHERE id_questao=?")) {
                    qs.setString(1, enun);
                    qs.setInt(2, idQ);
                    qs.executeUpdate();
                }
            }

            if (questaoSelecionada != -1) {
                for (int i=0;i<modeloAlternativas.getRowCount();i++) {
                    int idA = (int) modeloAlternativas.getValueAt(i,0);
                    String textoHtml = (String) modeloAlternativas.getValueAt(i,2);
                    String texto = stripHtml(textoHtml);
                    boolean correta = (Boolean) modeloAlternativas.getValueAt(i,3);
                    try (PreparedStatement as = conn.prepareStatement(
                            "UPDATE alternativa SET texto=?, correta=? WHERE id_alternativa=?")) {
                        as.setString(1, texto);
                        as.setBoolean(2, correta);
                        as.setInt(3, idA);
                        as.executeUpdate();
                    }
                }
            }

            conn.commit(); conn.setAutoCommit(oldAuto);
            JOptionPane.showMessageDialog(this, "Prova, questões e alternativas atualizadas.");
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ignore) {}
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        } finally { try { conn.setAutoCommit(true); } catch (Exception ignore) {} }
    }

    private void voltarAoMenuComConfirmacao() {
        int op = JOptionPane.showConfirmDialog(this,"Voltar ao menu? Alterações não salvas serão perdidas.","Confirmar",JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) { new Menu(conn, id_aluno, nome_completo, instrutor).setVisible(true); dispose(); }
    }

    private static String toHtmlWrapped(String s, int w) {
        if (s==null) s="";
        String safe = s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\n","<br>");
        return "<html><div style='width:"+w+"px;'>"+safe+"</div></html>";
    }
    private static String stripHtml(String html) {
        if (html==null) return "";
        return html.replaceAll("(?i)<br\\s*/?>","\n").replaceAll("<[^>]+>","");
    }

    static class HtmlWrapRenderer extends DefaultTableCellRenderer {
        private final int widthPx;
        HtmlWrapRenderer(int widthPx){ this.widthPx = widthPx; setVerticalAlignment(SwingConstants.TOP); }
        @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean s,boolean f,int r,int c){
            String html = v==null ? "" : v.toString();
            super.getTableCellRendererComponent(t, html, s, f, r, c);
            setText(html); setToolTipText(stripHtml(html)); return this;
        }
    }

    static class TextAreaCellEditor extends AbstractCellEditor implements TableCellEditor {
        private final JTextArea area; private final JScrollPane scroll; private final int widthPx;
        TextAreaCellEditor(int rows,int cols,int widthPx){ this.area=new JTextArea(rows,cols); area.setLineWrap(true); area.setWrapStyleWord(true); this.scroll=new JScrollPane(area); this.scroll.setPreferredSize(new Dimension(widthPx+20, rows*18+18)); this.widthPx=widthPx; }
        @Override public Object getCellEditorValue(){ return toHtmlWrapped(area.getText(), widthPx); }
        @Override public Component getTableCellEditorComponent(JTable t,Object v,boolean s,int r,int c){ String html=v==null?"":v.toString(); area.setText(stripHtml(html)); SwingUtilities.invokeLater(() -> area.requestFocusInWindow()); return scroll; }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/logistica","root","");
            SwingUtilities.invokeLater(() -> new EditarProvaCompleto(conn, 1).setVisible(true));
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}
