package GUI;

import Modelo.Instrutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.sql.*;

public class ExcluirProva extends JFrame {
    // contexto
    private final Connection conn;
    private final int id_aluno;
    private final String nome_completo;
    private final Instrutor instrutor;

    private JTable tabelaProvas;
    private JButton btnExcluir, btnFechar;

    // Construtor com contexto (use este a partir das telas)
    public ExcluirProva(Connection conn, int id_aluno, String nome_completo, Instrutor instrutor) {
        this.conn = conn;
        this.id_aluno = id_aluno;
        this.nome_completo = nome_completo;
        this.instrutor = instrutor;

        setTitle("Excluir Prova");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tabelaProvas = new JTable();
        carregarProvas();
        JScrollPane scroll = new JScrollPane(tabelaProvas);

        btnExcluir = new JButton("Excluir Prova");
        btnFechar = new JButton("Fechar");

        btnExcluir.addActionListener(e -> excluirProvaSelecionada());
        btnFechar.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(this, "Voltar ao menu?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                dispose();
                new Menu(conn, id_aluno, nome_completo, instrutor).setVisible(true);
            }
        });

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnFechar);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    // Overload para testes (main)
    public ExcluirProva(Connection conn) {
        this(conn, 0, "", null);
    }

    private void carregarProvas() {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID", "Descrição", "Data Aplicação"}, 0);
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT id_prova, descricao, data_aplicacao FROM prova")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_prova"),
                        rs.getString("descricao"),
                        rs.getString("data_aplicacao")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar provas: " + ex.getMessage());
        }
        tabelaProvas.setModel(modelo);
    }

    private void excluirProvaSelecionada() {
        int row = tabelaProvas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma prova para excluir.");
            return;
        }
        int idProva = (int) tabelaProvas.getValueAt(row, 0);
        int confirma = JOptionPane.showConfirmDialog(this, "Confirma exclusão da prova?", "Excluir", JOptionPane.YES_NO_OPTION);
        if (confirma != JOptionPane.YES_OPTION) return;

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement st = conn.prepareStatement(
                    "DELETE FROM alternativa WHERE id_questao IN (SELECT id_questao FROM questao WHERE id_prova = ?)")) {
                st.setInt(1, idProva);
                st.executeUpdate();
            }

            try (PreparedStatement st = conn.prepareStatement(
                    "DELETE FROM resposta_aluno WHERE id_questao IN (SELECT id_questao FROM questao WHERE id_prova = ?)")) {
                st.setInt(1, idProva);
                st.executeUpdate();
            }

            try (PreparedStatement st = conn.prepareStatement(
                    "DELETE FROM questao WHERE id_prova = ?")) {
                st.setInt(1, idProva);
                st.executeUpdate();
            }

            try (PreparedStatement st = conn.prepareStatement(
                    "DELETE FROM prova WHERE id_prova = ?")) {
                st.setInt(1, idProva);
                st.executeUpdate();
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "Prova e dados relacionados excluídos com sucesso!");
            carregarProvas();
        } catch (Exception ex) {
            try { conn.rollback(); } catch (Exception ignored) {}
            JOptionPane.showMessageDialog(this, "Erro ao excluir prova: " + ex.getMessage());
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ignored) {}
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/logistica","root","");
            SwingUtilities.invokeLater(() -> new ExcluirProva(conn).setVisible(true));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco: " + ex.getMessage());
        }
    }
}
