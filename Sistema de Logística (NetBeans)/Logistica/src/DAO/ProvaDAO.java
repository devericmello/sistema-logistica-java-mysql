package DAO;

import Factory.ConnectionFactory;
import Modelo.Prova;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProvaDAO {
    private Connection connection;

    public ProvaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(Prova prova) {
        String sql = "INSERT INTO prova (descricao, data_aplicacao, id_instrutor) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, prova.getdescricao());
            stmt.setString(2,   prova.getdata_aplicacao());
            stmt.setInt(3, prova.getid_instrutor());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar prova: ", e);
        }
    }

    public Prova buscar(int id) {
        String sql = "SELECT * FROM prova WHERE id_prova = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Prova prova = new Prova();
                prova.setid_prova(rs.getInt("id_prova"));
                prova.setdescricao(rs.getString("descricao"));
                prova.setdata_aplicacao(rs.getString("data_aplicacao"));
                prova.setid_instrutor(rs.getInt("id_instrutor"));
                return prova;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar prova: ", e);
        }
        return null;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM prova WHERE id_prova = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir prova: ", e);
        }
    }

    public List<Prova> listarTodos() {
        String sql = "SELECT * FROM prova";
        List<Prova> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Prova prova = new Prova();
                prova.setid_prova(rs.getInt("id_prova"));
                prova.setdescricao(rs.getString("descricao"));
                prova.setdata_aplicacao(rs.getString("data_aplicacao"));
                prova.setid_instrutor(rs.getInt("id_instrutor"));
                lista.add(prova);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar provas: ", e);
        }
        return lista;
    }
    
     public List<Prova> getProvasDisponiveis(int idAluno) throws SQLException {
        String sql = "SELECT * FROM prova WHERE id_prova NOT IN (SELECT id_prova FROM provas_realizada WHERE id_aluno = ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idAluno);
        ResultSet rs = stmt.executeQuery();
        List<Prova> provas = new ArrayList<>();
        while(rs.next()){
            Prova p = new Prova();
            p.setid_prova(rs.getInt("id_prova"));
            p.setdescricao(rs.getString("descricao"));
            provas.add(p);
        }
        return provas;
    }
    // Adicione outros métodos similares para questões, alternativas etc.
}

