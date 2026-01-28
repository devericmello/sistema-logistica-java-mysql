package DAO;

import Factory.ConnectionFactory;
import Modelo.Simulacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimulacaoDAO {
    private Connection connection;

    public SimulacaoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(Simulacao simulacao) {
        String sql = "INSERT INTO simulacao (descricao, objetivo) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, simulacao.getdescricao());
            stmt.setString(2, simulacao.getobjetivo());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar simulação: ", e);
        }
    }

    public Simulacao buscar(int id) {
        String sql = "SELECT * FROM simulacao WHERE id_simulacao = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Simulacao simulacao = new Simulacao();
                simulacao.setid_simulacao(rs.getInt("id_simulacao"));
                simulacao.setdescricao(rs.getString("descricao"));
                simulacao.setobjetivo(rs.getString("objetivo"));
                return simulacao;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar simulação: ", e);
        }
        return null;
    }

    public void atualizar(Simulacao simulacao) {
        String sql = "UPDATE simulacao SET descricao=?, objetivo=? WHERE id_simulacao=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, simulacao.getdescricao());
            stmt.setString(2, simulacao.getobjetivo());
            stmt.setInt(3, simulacao.getid_simulacao());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar simulação: ", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM simulacao WHERE id_simulacao=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir simulação: ", e);
        }
    }

    public List<Simulacao> listarTodos() {
        String sql = "SELECT * FROM simulacao";
        List<Simulacao> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Simulacao simulacao = new Simulacao();
                simulacao.setid_simulacao(rs.getInt("id_simulacao"));
                simulacao.setdescricao(rs.getString("descricao"));
                simulacao.setobjetivo(rs.getString("objetivo"));
                lista.add(simulacao);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar simulações: ", e);
        }
        return lista;
    }
}
