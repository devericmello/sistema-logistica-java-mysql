package DAO;

import Factory.ConnectionFactory;
import Modelo.ElaboraProva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElaboraProvaDAO {
    private Connection connection;

    public ElaboraProvaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(ElaboraProva ep) {
        String sql = "INSERT INTO elabora_prova (id_instrutor, id_prova) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ep.getid_instrutor());
            stmt.setInt(2, ep.getid_prova());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar elabora_prova: ", e);
        }
    }

    public ElaboraProva buscar(int idInstrutor, int idProva) {
        String sql = "SELECT * FROM elabora_prova WHERE id_instrutor = ? AND id_prova = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idInstrutor);
            stmt.setInt(2, idProva);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ElaboraProva ep = new ElaboraProva();
                ep.setid_instrutor(rs.getInt("id_instrutor"));
                ep.setid_prova(rs.getInt("id_prova"));
                return ep;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar elabora_prova: ", e);
        }
        return null;
    }

    public void excluir(int idInstrutor, int idProva) {
        String sql = "DELETE FROM elabora_prova WHERE id_instrutor = ? AND id_prova = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idInstrutor);
            stmt.setInt(2, idProva);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir elabora_prova: ", e);
        }
    }

    public List<ElaboraProva> listarTodos() {
        String sql = "SELECT * FROM elabora_prova";
        List<ElaboraProva> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ElaboraProva ep = new ElaboraProva();
                ep.setid_instrutor(rs.getInt("id_instrutor"));
                ep.setid_prova(rs.getInt("id_prova"));
                lista.add(ep);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar elabora_prova: ", e);
        }
        return lista;
    }
}
