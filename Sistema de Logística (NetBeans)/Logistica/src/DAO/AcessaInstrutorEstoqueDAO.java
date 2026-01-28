package DAO;

import Factory.ConnectionFactory;
import Modelo.AcessaInstrutorEstoque;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcessaInstrutorEstoqueDAO {
    private Connection connection;

    public AcessaInstrutorEstoqueDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(AcessaInstrutorEstoque acesso) {
        String sql = "INSERT INTO acessa_instrutor_estoque (id_instrutor, id_estoque) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, acesso.getid_instrutor());
            stmt.setInt(2, acesso.getid_estoque());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar acesso: ", e);
        }
    }

    public AcessaInstrutorEstoque buscar(int idInstrutor, int idEstoque) {
        String sql = "SELECT * FROM acessa_instrutor_estoque WHERE id_instrutor = ? AND id_estoque = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idInstrutor);
            stmt.setInt(2, idEstoque);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                AcessaInstrutorEstoque acesso = new AcessaInstrutorEstoque();
                acesso.setid_instrutor(rs.getInt("id_instrutor"));
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

    public void excluir(int idInstrutor, int idEstoque) {
        String sql = "DELETE FROM acessa_instrutor_estoque WHERE id_instrutor = ? AND id_estoque = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idInstrutor);
            stmt.setInt(2, idEstoque);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir acesso: ", e);
        }
    }

    public List<AcessaInstrutorEstoque> listarTodos() {
        String sql = "SELECT * FROM acessa_instrutor_estoque";
        List<AcessaInstrutorEstoque> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AcessaInstrutorEstoque acesso = new AcessaInstrutorEstoque();
                acesso.setid_instrutor(rs.getInt("id_instrutor"));
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
