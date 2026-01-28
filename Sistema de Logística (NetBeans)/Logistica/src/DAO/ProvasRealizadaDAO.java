package DAO;

import Factory.ConnectionFactory;
import Modelo.ProvasRealizada;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProvasRealizadaDAO {
    private Connection connection;

    public ProvasRealizadaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(ProvasRealizada pr) {
        String sql = "INSERT INTO provas_realizada (id_aluno, id_prova) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, pr.getid_aluno());
            stmt.setInt(2, pr.getid_prova());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar prova realizada: ", e);
        }
    }

    public ProvasRealizada buscar(int idAluno, int idProva) {
        String sql = "SELECT * FROM provas_realizada WHERE id_aluno = ? AND id_prova = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idProva);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ProvasRealizada pr = new ProvasRealizada();
                pr.setid_aluno(rs.getInt("id_aluno"));
                pr.setid_prova(rs.getInt("id_prova"));
                return pr;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar prova realizada: ", e);
        }
        return null;
    }

    public void excluir(int idAluno, int idProva) {
        String sql = "DELETE FROM provas_realizada WHERE id_aluno = ? AND id_prova = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idProva);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir prova realizada: ", e);
        }
    }

    public List<ProvasRealizada> listarTodos() {
        String sql = "SELECT * FROM provas_realizada";
        List<ProvasRealizada> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProvasRealizada pr = new ProvasRealizada();
                pr.setid_aluno(rs.getInt("id_aluno"));
                pr.setid_prova(rs.getInt("id_prova"));
                lista.add(pr);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar provas realizadas: ", e);
        }
        return lista;
    }
    
    public List<ProvasRealizada> listarPorAluno(int id_aluno) {
    String sql = "SELECT * FROM provas_realizada WHERE id_aluno = ?";
    List<ProvasRealizada> lista = new ArrayList<>();
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id_aluno);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            ProvasRealizada pr = new ProvasRealizada();
            pr.setid_aluno(rs.getInt("id_aluno"));
            pr.setid_prova(rs.getInt("id_prova"));
            lista.add(pr);
        }

        rs.close();
        stmt.close();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar provas realizadas do aluno: ", e);
    }
    return lista;
}

}
