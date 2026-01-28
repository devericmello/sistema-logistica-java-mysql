package DAO;

import Factory.ConnectionFactory;
import Modelo.EstoqueMovimentacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueMovimentacaoDAO {
    private Connection connection;

    public EstoqueMovimentacaoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(EstoqueMovimentacao movimentacao) {
        String sql = "INSERT INTO estoque_movimentacao (id_estoque, quantidade, data_movimentacao, motivo) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, movimentacao.getid_estoque());
            stmt.setString(2, movimentacao.getquantidade());
            stmt.setString(3, movimentacao.getdata_movimentacao());
            stmt.setString(4, movimentacao.getmotivo());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar movimentação: ", e);
        }
    }

    public EstoqueMovimentacao buscar(int id) {
        String sql = "SELECT * FROM estoque_movimentacao WHERE id_estoque_movimentacao = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                EstoqueMovimentacao mov = new EstoqueMovimentacao();
                mov.setid_estoque_movimentacao(rs.getInt("id_estoque_movimentacao"));
                mov.setid_estoque(rs.getInt("id_estoque"));
                mov.setquantidade(rs.getString("quantidade"));
                mov.setdata_movimentacao(rs.getString("data_movimentacao"));
                mov.setmotivo(rs.getString("motivo"));
                return mov;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar movimentação: ", e);
        }
        return null;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM estoque_movimentacao WHERE id_estoque_movimentacao = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir movimentação: ", e);
        }
    }

    public List<EstoqueMovimentacao> listarTodos() {
        String sql = "SELECT * FROM estoque_movimentacao";
        List<EstoqueMovimentacao> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EstoqueMovimentacao mov = new EstoqueMovimentacao();
                mov.setid_estoque_movimentacao(rs.getInt("id_estoque_movimentacao"));
                mov.setid_estoque(rs.getInt("id_estoque"));
                mov.setquantidade(rs.getString("quantidade"));
                mov.setdata_movimentacao(rs.getString("data_movimentacao"));
                mov.setmotivo(rs.getString("motivo"));
                lista.add(mov);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar movimentações: ", e);
        }
        return lista;
    }
}
