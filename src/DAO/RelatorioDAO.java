package DAO;

import Factory.ConnectionFactory;
import Modelo.Relatorio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {
    private Connection connection;

    public RelatorioDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(Relatorio relatorio) {
        String sql = "INSERT INTO relatorio (descricao, tipo_relatorio, data_criacao) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, relatorio.getdescricao());
            stmt.setString(2, relatorio.gettipo_relatorio());
            stmt.setString(3, relatorio.getdata_criacao());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar relatorio: ", e);
        }
    }

    public Relatorio buscar(int id) {
        String sql = "SELECT * FROM relatorio WHERE id_relatorio = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Relatorio relatorio = new Relatorio();
                relatorio.setid_relatorio(rs.getInt("id_relatorio"));
                relatorio.setdescricao(rs.getString("descricao"));
                relatorio.settipo_relatorio(rs.getString("tipo_relatorio"));
                relatorio.setdata_criacao(rs.getString("data_criacao"));
                return relatorio;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relatorio: ", e);
        }
        return null;
    }

    public void atualizar(Relatorio relatorio) {
        String sql = "UPDATE relatorio SET descricao=?, tipo_relatorio=?, data_criacao=? WHERE id_relatorio=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, relatorio.getdescricao());
            stmt.setString(2, relatorio.gettipo_relatorio());
            stmt.setString(3, relatorio.getdata_criacao());
            stmt.setInt(4, relatorio.getid_relatorio());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar relatorio: ", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM relatorio WHERE id_relatorio=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir relatorio: ", e);
        }
    }

    public List<Relatorio> listarTodos() {
        String sql = "SELECT * FROM relatorio";
        List<Relatorio> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Relatorio relatorio = new Relatorio();
                relatorio.setid_relatorio(rs.getInt("id_relatorio"));
                relatorio.setdescricao(rs.getString("descricao"));
                relatorio.settipo_relatorio(rs.getString("tipo_relatorio"));
                relatorio.setdata_criacao(rs.getString("data_criacao"));
                lista.add(relatorio);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar relatorios: ", e);
        }
        return lista;
    }
}
