package GUI;

import javax.swing.*;
import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;

import DAO.QuestaoDAO;
import DAO.AlternativaDAO;
import DAO.RespostaAlunoDAO;
import DAO.DesempenhoDAO;
import Modelo.Questao;
import Modelo.Alternativa;
import Modelo.RespostaAluno;
import Modelo.Desempenho;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.File;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;



public class RealizarProvaAluno extends javax.swing.JFrame {

    private final int id_aluno;
    private final int id_prova;
    private final Connection conn;
    private final String nome_completo;

    private List<Questao> questoes = new ArrayList<>();
    private int questaoAtual = 0;
    private List<Alternativa> alternativasQuestaoAtual = new ArrayList<>();
    private final Map<Integer, Integer> cacheEscolhas = new HashMap<>();
    private int totalQuestoes = 0;
    // ==== componentes gerenciados pelo GUI Builder ====
    // certifique-se de criar estes nomes no Designer:
    

    public RealizarProvaAluno(Connection conn, int id_aluno, int id_prova, String nome_completo) {
        this.conn = conn;
        this.id_aluno = id_aluno;
        this.id_prova = id_prova;
        this.nome_completo = nome_completo;

        initComponents();          // gerado pelo NetBeans
        configurarEventosUI();     // listeners dos botões
        if (lblImagem != null) { lblImagem.setText(""); lblImagem.setIcon(null); }


        carregarQuestoes();
        totalQuestoes = questoes != null ? questoes.size() : 0;
        if (totalQuestoes > 0) mostrarQuestao(0);
    }

    // =================== LISTENERS DOS BOTÕES ===================
    private void configurarEventosUI() {
        if (lblQuestao != null) lblQuestao.setFont(lblQuestao.getFont().deriveFont(Font.BOLD, 16f));
    if (lblImagem != null) { 
        lblImagem.setText(""); 
        lblImagem.setIcon(null); 
        // REMOVA essa linha:
        // lblImagem.setVisible(false);
    }
    }
    
    // Mostra/oculta a imagem da questão (limite ~900x360 na tela)
private void renderImagem(byte[] bytes) {
    try {
        if (lblImagem == null || bytes == null || bytes.length == 0) {
            if (lblImagem != null) { lblImagem.setIcon(null); lblImagem.setText(""); lblImagem.setVisible(false); }
            return;
        }
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bytes));
        if (bi == null) {
            lblImagem.setIcon(null); lblImagem.setText(""); lblImagem.setVisible(false);
            return;
        }
        lblImagem.setIcon(scaleToFit(bi, 900, 360)); // ajuste se quiser
        lblImagem.setText(null);
        lblImagem.setVisible(true);
    } catch (Exception ex) {
        if (lblImagem != null) { lblImagem.setIcon(null); lblImagem.setText(""); lblImagem.setVisible(false); }
    }
}

// Redimensiona preservando proporção
private ImageIcon scaleToFit(BufferedImage img, int maxW, int maxH) {
    int w = img.getWidth(), h = img.getHeight();
    double s = Math.min((double) maxW / w, (double) maxH / h);
    if (s > 1) s = 1; // não amplia além do original
    int nw = (int) Math.round(w * s), nh = (int) Math.round(h * s);
    Image scaled = img.getScaledInstance(nw, nh, Image.SCALE_SMOOTH);
    return new ImageIcon(scaled);
}


    // =================== LÓGICA DE PROVA ===================
    private void carregarQuestoes() {
        try {
            QuestaoDAO questaoDAO = new QuestaoDAO(conn);
            questoes = questaoDAO.listarPorProva(id_prova);
            Collections.shuffle(questoes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar questões: " + e.getMessage());
            questoes = new ArrayList<>();
        }
    }
    private void carregarImagemQuestao(Questao q) {
    try {
        // tenta BLOB primeiro
        byte[] blob = null;
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT imagem FROM questao WHERE id_questao=?")) {
            ps.setInt(1, q.getIdQuestao());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) blob = rs.getBytes(1);
            }
        }

        BufferedImage img = null;
        if (blob != null && blob.length > 0) {
            img = ImageIO.read(new ByteArrayInputStream(blob));
        } else {
            // fallback: caminho no disco
            String path = null;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT imagem_path FROM questao WHERE id_questao=?")) {
                ps.setInt(1, q.getIdQuestao());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) path = rs.getString(1);
                }
            }
            if (path != null && !path.trim().isEmpty()) {
                File f = new File(path);
                if (f.exists()) img = ImageIO.read(f);
            }
        }

        if (img == null) {               // sem imagem
            if (lblImagem != null) { lblImagem.setIcon(null); lblImagem.setText(""); }
            return;
        }

        // redimensiona para caber no layout
        int maxW = 900, maxH = 320;
        int w = img.getWidth(), h = img.getHeight();
        double k = Math.min((double) maxW / w, (double) maxH / h);
        if (k < 1.0) {
            w = (int) Math.round(w * k);
            h = (int) Math.round(h * k);
            img = toBuffered(img.getScaledInstance(w, h, Image.SCALE_SMOOTH));
        }

        lblImagem.setText("");
        lblImagem.setIcon(new ImageIcon(img));
    } catch (Exception ignore) {
        if (lblImagem != null) { lblImagem.setIcon(null); lblImagem.setText(""); }
    }
}

private static BufferedImage toBuffered(Image img) {
    if (img instanceof BufferedImage) return (BufferedImage) img;
    BufferedImage b = new BufferedImage(img.getWidth(null), img.getHeight(null),
                                        BufferedImage.TYPE_INT_ARGB);
    b.getGraphics().drawImage(img, 0, 0, null);
    b.getGraphics().dispose();
    return b;
}


    private void mostrarQuestao(int index) {
    if (index < 0 || index >= questoes.size()) return;

    Questao q = questoes.get(index);

    // Enunciado com quebra automática
    lblQuestao.setText("<html><body style='width:760px; font-size:16px;'>"
            + "Questão " + (index + 1) + " de " + totalQuestoes + ": "
            + q.getEnunciado() + "</body></html>");

    // Imagem (byte[] vindo de Questao.getImagem()) — mostra/oculta automaticamente
    carregarImagemQuestao(q);
    

    try {
        AlternativaDAO alternativaDAO = new AlternativaDAO(conn);
        List<Alternativa> alternativas = alternativaDAO.listarPorQuestao(q.getIdQuestao());
        Collections.shuffle(alternativas);
        alternativasQuestaoAtual = alternativas;

        carregarQuandoVazio();

        JRadioButton[] radios = {rbA, rbB, rbC, rbD, rbE};
        for (int i = 0; i < alternativas.size() && i < radios.length; i++) {
            radios[i].setVisible(true);
            radios[i].setText("<html><body style='width:700px; font-size:14px;'>"
                    + (char) ('A' + i) + ") " + alternativas.get(i).getTexto()
                    + "</body></html>");
        }
        buttonGroup1.clearSelection();

        Integer idAltEscolhida = cacheEscolhas.get(q.getIdQuestao());
        if (idAltEscolhida != null) {
            for (int i = 0; i < alternativas.size() && i < radios.length; i++) {
                if (alternativas.get(i).getIdAlternativa() == idAltEscolhida) {
                    radios[i].setSelected(true);
                    break;
                }
            }
        }

        btnAnterior.setEnabled(index > 0);
        btnFinalizar.setEnabled(index == totalQuestoes - 1);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar alternativas: " + e.getMessage());
    }
}


    private void carregarQuandoVazio() {
        rbA.setVisible(false); rbB.setVisible(false); rbC.setVisible(false); rbD.setVisible(false); rbE.setVisible(false);
        rbA.setText(""); rbB.setText(""); rbC.setText(""); rbD.setText(""); rbE.setText("");
        buttonGroup1.clearSelection();
    }
    
    

    private boolean temSelecao() {
        return buttonGroup1.getSelection() != null && !alternativasQuestaoAtual.isEmpty();
    }

    private int getAlternativaEscolhida() {
        JRadioButton[] radios = {rbA, rbB, rbC, rbD, rbE};
        for (int i = 0; i < alternativasQuestaoAtual.size() && i < radios.length; i++) {
            if (radios[i].isSelected()) return alternativasQuestaoAtual.get(i).getIdAlternativa();
        }
        return -1;
    }

    private boolean salvarRespostaAtual() {
        if (!temSelecao()) {
            JOptionPane.showMessageDialog(this, "Selecione uma alternativa!");
            return false;
        }
        int idAlternativa = getAlternativaEscolhida();
        if (idAlternativa == -1) return false;

        Questao q = questoes.get(questaoAtual);
        cacheEscolhas.put(q.getIdQuestao(), idAlternativa);

        try {
            RespostaAlunoDAO respostaDAO = new RespostaAlunoDAO(conn);
            RespostaAluno resp = new RespostaAluno();
            resp.setIdAluno(id_aluno);
            resp.setIdQuestao(q.getIdQuestao());
            resp.setIdAlternativaEscolhida(idAlternativa);
            resp.setDataResposta(new java.util.Date());
            respostaDAO.inserir(resp);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar resposta: " + e.getMessage());
            return false;
        }
    }

    private void irParaQuestao(int idx) {
        if (idx < 0 || idx >= totalQuestoes) return;
        questaoAtual = idx;
        mostrarQuestao(questaoAtual);
    }
    
    

    private int calcularAcertos() {
        int acertos = 0;
        try {
            RespostaAlunoDAO respostaDAO = new RespostaAlunoDAO(conn);
            List<RespostaAluno> respostas = respostaDAO.listarPorAlunoEProva(id_aluno, id_prova);

            Map<Integer, RespostaAluno> ultimaRespostaPorQuestao = new HashMap<>();
            for (RespostaAluno r : respostas) ultimaRespostaPorQuestao.put(r.getIdQuestao(), r);

            AlternativaDAO alternativaDAO = new AlternativaDAO(conn);
            for (RespostaAluno r : ultimaRespostaPorQuestao.values()) {
                Alternativa a = alternativaDAO.buscar(r.getIdAlternativaEscolhida());
                if (a != null && a.isCorreta()) acertos++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular acertos: " + e.getMessage());
        }
        return acertos;
    }

    private void finalizarProva() {
        try {
            QuestaoDAO questaoDAO = new QuestaoDAO(conn);
            int total = questaoDAO.listarPorProva(id_prova).size();

            int acertos = calcularAcertos();
            double percentual = total > 0 ? (double) acertos / total * 100.0 : 0;
            double nota = total > 0 ? (double) acertos / total * 10.0 : 0;

            Desempenho d = new Desempenho();
            d.setid_aluno(id_aluno);
            d.setid_prova(id_prova);
            d.setnota_global(nota);
            d.setpercentual_acerto(percentual);
            d.setdata_avaliacao(new java.util.Date());
            d.setstatus("finalizada");
            d.setobservacao("Prova finalizada pelo sistema");

            DesempenhoDAO desempenhoDAO = new DesempenhoDAO();
            desempenhoDAO.cadastro(d);

            JOptionPane.showMessageDialog(this, "Prova finalizada!\nNota: " + nota + "\nAcerto: " + percentual + "%");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao finalizar prova: " + e.getMessage());
        }
    }

    // ====== deixe o NetBeans gerar este método e os campos acima ======
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        lblQuestao = new javax.swing.JLabel();
        rbA = new javax.swing.JRadioButton();
        rbB = new javax.swing.JRadioButton();
        rbC = new javax.swing.JRadioButton();
        rbD = new javax.swing.JRadioButton();
        rbE = new javax.swing.JRadioButton();
        btnResponder = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        btnAnterior = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        lblImagem = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblQuestao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblQuestao.setText("Enunciado Aqui");

        buttonGroup1.add(rbA);
        rbA.setText("A)");
        rbA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbB);
        rbB.setText("B)");

        buttonGroup1.add(rbC);
        rbC.setText("C)");

        buttonGroup1.add(rbD);
        rbD.setText("D)");

        buttonGroup1.add(rbE);
        rbE.setText("E)");

        btnResponder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnResponder.setText("Responder");
        btnResponder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResponderActionPerformed(evt);
            }
        });

        btnFinalizar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Avaliação");

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setText("Voltar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/vire-a-esquerda.png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/menu1.png"))); // NOI18N

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setText("Sair");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/sair.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jLabel12))
                .addGap(19, 19, 19))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logistica.png"))); // NOI18N

        jSeparator3.setForeground(new java.awt.Color(0, 51, 102));

        jLabel13.setFont(new java.awt.Font("Simplified Arabic Fixed", 1, 28)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 102));
        jLabel13.setText("Avaliação de Logística");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel10)
                .addGap(346, 346, 346)
                .addComponent(jLabel13)
                .addContainerGap(601, Short.MAX_VALUE))
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)))
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        btnAnterior.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAnterior.setText("Voltar a Questão Anterior");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar Alternativa");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnResponder, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(rbB)
                                        .addComponent(rbA)
                                        .addComponent(rbC)
                                        .addComponent(rbD)
                                        .addComponent(rbE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(btnLimpar))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(btnAnterior))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblQuestao)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(59, 59, 59)
                                        .addComponent(lblImagem)))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(lblQuestao)
                .addGap(28, 28, 28)
                .addComponent(lblImagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 411, Short.MAX_VALUE)
                .addComponent(rbA)
                .addGap(18, 18, 18)
                .addComponent(rbB)
                .addGap(18, 18, 18)
                .addComponent(rbC)
                .addGap(18, 18, 18)
                .addComponent(rbD)
                .addGap(18, 18, 18)
                .addComponent(rbE)
                .addGap(18, 18, 18)
                .addComponent(btnLimpar)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResponder, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rbAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbAActionPerformed

    private void btnResponderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResponderActionPerformed
       if (questaoAtual < totalQuestoes) {
        if (!salvarRespostaAtual()) return; // não avança sem seleção
        if (questaoAtual < totalQuestoes - 1) {
            irParaQuestao(questaoAtual + 1);
        } else {
            btnResponder.setEnabled(false);
            btnFinalizar.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Você respondeu todas as questões. Clique em Finalizar!");
        }
    }
    }//GEN-LAST:event_btnResponderActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
  try {
        QuestaoDAO questaoDAO = new QuestaoDAO(conn);
        int totalQuestoes = questaoDAO.listarPorProva(id_prova).size(); // Sempre do banco!

        int acertos = calcularAcertos();

        double percentual = totalQuestoes > 0 ? (double) acertos / totalQuestoes * 100.0 : 0;
        double nota = totalQuestoes > 0 ? (double) acertos / totalQuestoes * 10.0 : 0;

        Desempenho desempenho = new Desempenho();
        desempenho.setid_aluno(id_aluno);
        desempenho.setid_prova(id_prova);
        desempenho.setnota_global(nota);
        desempenho.setpercentual_acerto(percentual);
        desempenho.setdata_avaliacao(new java.util.Date());
        desempenho.setstatus("finalizada");
        desempenho.setobservacao("Prova finalizada pelo sistema");

        DesempenhoDAO desempenhoDAO = new DesempenhoDAO();
        desempenhoDAO.cadastro(desempenho);

        JOptionPane.showMessageDialog(this, "Prova finalizada!\nNota: " + nota + "\nAcerto: " + percentual + "%");

        // Aqui é onde a tela MenuAluno será aberta
        MenuAluno menuAluno = new MenuAluno(conn, id_aluno, nome_completo);
        menuAluno.setVisible(true);
        this.dispose(); // Fecha a janela atual (caso necessário)

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao finalizar prova: " + e.getMessage());
    }
    // TODO add your handling code here:
  // TODO add your handling code here:
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
MenuAluno menuAluno = new MenuAluno (conn, id_aluno, nome_completo);  
menuAluno.setVisible(true);
this.dispose();
// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
buttonGroup1.clearSelection();
    if (questaoAtual < totalQuestoes) {
        int idQ = questoes.get(questaoAtual).getIdQuestao();
        cacheEscolhas.remove(idQ);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
if (questaoAtual > 0) irParaQuestao(questaoAtual - 1);        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnteriorActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnResponder;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblImagem;
    private javax.swing.JLabel lblQuestao;
    private javax.swing.JRadioButton rbA;
    private javax.swing.JRadioButton rbB;
    private javax.swing.JRadioButton rbC;
    private javax.swing.JRadioButton rbD;
    private javax.swing.JRadioButton rbE;
    // End of variables declaration//GEN-END:variables
}
