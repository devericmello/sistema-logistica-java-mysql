package DAO;

import Factory.ConnectionFactory;
import Modelo.Estoque;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {
    private Connection connection;

    public EstoqueDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(Estoque estoque) {
        String sql = "INSERT INTO estoque (descricao, localizacao) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, estoque.getdescricao());
            stmt.setString(2, estoque.getlocalizacao());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar estoque: ", e);
        }
    }

    public Estoque buscar(int id_estoque) {
        String sql = "SELECT * FROM estoque WHERE id_estoque = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_estoque);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Estoque e = new Estoque();
                e.setid_estoque(rs.getInt("id_estoque"));
                e.setdescricao(rs.getString("descricao"));
                e.setlocalizacao(rs.getString("localizacao"));
                return e;
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar estoque: ", ex);
        }
        return null;
    }

    public void excluir(int id_estoque) {
        String sql = "DELETE FROM estoque WHERE id_estoque = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_estoque);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir estoque: ", e);
        }
    }

    public List<Estoque> listarTodos() {
        String sql = "SELECT * FROM estoque";
        List<Estoque> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Estoque e = new Estoque();
                e.setid_estoque(rs.getInt("id_estoque"));
                e.setdescricao(rs.getString("descricao"));
                e.setlocalizacao(rs.getString("localizacao"));
                lista.add(e);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar estoques: ", e);
        }
        return lista;
    }
}
