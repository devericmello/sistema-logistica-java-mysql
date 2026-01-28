package DAO;

import Factory.ConnectionFactory;
import Modelo.AcessaCoordenadorEstoque;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcessaCoordenadorEstoqueDAO {
    private Connection connection;

    public AcessaCoordenadorEstoqueDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(AcessaCoordenadorEstoque acesso) {
        String sql = "INSERT INTO acessa_coordenador_estoque (id_coordenador, id_estoque) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, acesso.getid_coordenador());
            stmt.setInt(2, acesso.getid_estoque());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar acesso: ", e);
        }
    }

    public AcessaCoordenadorEstoque buscar(int id_coordenador, int id_estoque) {
        String sql = "SELECT * FROM acessa_coordenador_estoque WHERE id_coordenador = ? AND id_estoque = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_coordenador);
            stmt.setInt(2, id_estoque);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                AcessaCoordenadorEstoque acesso = new AcessaCoordenadorEstoque();
                acesso.setid_coordenador(rs.getInt("id_coordenador"));
                acesso.setid_estoque(rs.getInt("id_estoque"));
                return acesso;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar acesso: ", e);
        }
        return null;
    }

    public void excluir(int id_coordenador, int id_estoque) {
        String sql = "DELETE FROM acessa_coordenador_estoque WHERE id_coordenador = ? AND id_estoque = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_coordenador);
            stmt.setInt(2, id_estoque);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir acesso: ", e);
        }
    }

    public List<AcessaCoordenadorEstoque> listarTodos() {
        String sql = "SELECT * FROM acessa_coordenador_estoque";
        List<AcessaCoordenadorEstoque> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AcessaCoordenadorEstoque acesso = new AcessaCoordenadorEstoque();
                acesso.setid_coordenador(rs.getInt("id_coordenador"));
                acesso.setid_estoque(rs.getInt("id_estoque"));
                lista.add(acesso);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar acessos: ", e);
        }
        return lista;
    }
}
