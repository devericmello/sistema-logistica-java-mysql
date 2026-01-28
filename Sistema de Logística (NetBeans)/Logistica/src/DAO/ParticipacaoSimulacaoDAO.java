package DAO;

import Factory.ConnectionFactory;
import Modelo.ParticipacaoSimulacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipacaoSimulacaoDAO {
    private Connection connection;

    public ParticipacaoSimulacaoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(ParticipacaoSimulacao ps) {
        String sql = "INSERT INTO participacao_simulacao (id_simulacao, id_material) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ps.getid_simulacao());
            stmt.setInt(2, ps.getid_material());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar participação em simulação: ", e);
        }
    }

    public ParticipacaoSimulacao buscar(int idSimulacao, int idMaterial) {
        String sql = "SELECT * FROM participacao_simulacao WHERE id_simulacao = ? AND id_material = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idSimulacao);
            stmt.setInt(2, idMaterial);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ParticipacaoSimulacao ps = new ParticipacaoSimulacao();
                ps.setid_simulacao(rs.getInt("id_simulacao"));
                ps.setid_material(rs.getInt("id_material"));
                return ps;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar participação em simulação: ", e);
        }
        return null;
    }

    public void excluir(int idSimulacao, int idMaterial) {
        String sql = "DELETE FROM participacao_simulacao WHERE id_simulacao = ? AND id_material = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idSimulacao);
            stmt.setInt(2, idMaterial);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir participação em simulação: ", e);
        }
    }

    public List<ParticipacaoSimulacao> listarTodos() {
        String sql = "SELECT * FROM participacao_simulacao";
        List<ParticipacaoSimulacao> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ParticipacaoSimulacao ps = new ParticipacaoSimulacao();
                ps.setid_simulacao(rs.getInt("id_simulacao"));
                ps.setid_material(rs.getInt("id_material"));
                lista.add(ps);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar participações em simulações: ", e);
        }
        return lista;
    }
}
