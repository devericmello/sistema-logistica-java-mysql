/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.AlunoDAO;
import Modelo.Aluno;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.Connection;
import javax.swing.JFrame;
import DAO.AlunoDAO;
import DAO.ProvaDAO;
import DAO.QuestaoDAO;
import DAO.RelatorioDAO;
import DAO.AlternativaDAO;
import DAO.RespostaAlunoDAO;
import GUI.CadastrarProva;
import GUI.TelaComVoltar;
import Modelo.Aluno;
import Modelo.Questao;
import Modelo.Relatorio;
import Modelo.Alternativa;
import Modelo.RespostaAluno;
import GUI.Login;
import GUI.Menu;
import GUI.EditarProvaCompleto;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import Modelo.Instrutor;
import DAO.InstrutorDAO;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import GUI.MenuCoordenador;

/**
 *
 * @author Eric
 */
public class AlunosCadastradosDIY extends JFrame {

    // --- contexto para voltar ao MenuCoordenador
    private final Connection conn;
    private final int idCoordenador;          // passe o id que você já tem no MenuCoordenador
    private final String nomeCoordenador;     // passe o nome que você já tem no MenuCoordenador

    // --- UI
    private JTable tbl;
    private DefaultTableModel model;
    private JTextField txtQtd;

    // Construtor usado pela aplicação (recomendado)
    public AlunosCadastradosDIY(Connection conn, int idCoordenador, String nomeCoordenador) {
        this.conn = conn;
        this.idCoordenador = idCoordenador;
        this.nomeCoordenador = nomeCoordenador;
        buildUI();
    }

    // Construtor só para testes/preview (abre sem contexto)
    public AlunosCadastradosDIY() {
        this(null, 0, "");
    }

    private void buildUI() {
        setTitle("Alunos Cadastrados");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1280, 720);                 // janela maior
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- TOP (Quantidade + comandos)
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topo.add(new JLabel("Quantidade:"));

        txtQtd = new JTextField(6);
        txtQtd.setEnabled(false);           // desabilitado (cinza) e não editável
        topo.add(txtQtd);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> carregarTabela());
        topo.add(btnAtualizar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            // Abre o MenuCoordenador com o mesmo contexto e fecha esta janela
            SwingUtilities.invokeLater(() -> {
                new MenuCoordenador(conn, idCoordenador, nomeCoordenador).setVisible(true);
                dispose();
            });
        });
        topo.add(btnVoltar);

        add(topo, BorderLayout.NORTH);

        // ---------- TABELA (centro)
        model = new DefaultTableModel(new Object[]{
                "ID","Nome","Data Nasc.","Sexo","Telefone","Email","Turma",
                "Número","Bairro","CEP","Rua","Nacionalidade","Cidade","UF"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tbl = new JTable(model);
        tbl.setAutoCreateRowSorter(true);
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        // ---------- Painel lateral (opcional, apenas informativo)
        JPanel lateral = new JPanel();
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setBorder(new TitledBorder("Informações (livre)"));
        JLabel info1 = new JLabel("• Clique em Atualizar para listar.");
        info1.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(Box.createVerticalStrut(8));
        lateral.add(info1);
        lateral.add(Box.createVerticalGlue());
        add(lateral, BorderLayout.EAST);

        carregarTabela();
    }

    // Carrega JTable a partir do DAO
    private void carregarTabela() {
        model.setRowCount(0);

        List<Aluno> alunos = new AlunoDAO().listarTodos(); // garanta que este método existe no seu DAO
        for (Aluno a : alunos) {
            model.addRow(new Object[]{
                    a.getid_aluno(),
                    nz(a.getnome_completo()),
                    fmtData(nz(a.getdata_nascimento())),   // yyyy-MM-dd -> yyyy/MM/dd
                    nz(a.getsexo()),
                    nz(a.gettelefone()),
                    nz(a.getemail()),
                    nz(a.getturma()),
                    nz(a.getnumero()),
                    nz(a.getbairro()),
                    nz(a.getcep()),
                    nz(a.getrua()),
                    nz(a.getnacionalidade()),
                    nz(a.getcidade()),
                    nz(a.getUF())
            });
        }
        txtQtd.setText(String.valueOf(model.getRowCount()));
    }

    private static String nz(String s){ return s == null ? "" : s; }
    private static String fmtData(String db){
        if (db.matches("\\d{4}-\\d{2}-\\d{2}")) return db.replace('-', '/'); // yyyy/MM/dd
        if (db.matches("\\d{8}")) return db.substring(0,4)+"/"+db.substring(4,6)+"/"+db.substring(6,8);
        return db;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 891, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 586, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(AlunosCadastradosDIY.class.getName())
                .log(java.util.logging.Level.SEVERE, null, ex);
    }

    java.awt.EventQueue.invokeLater(() -> new AlunosCadastradosDIY().setVisible(true));
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
