package DAO;

import Factory.ConnectionFactory;
import Modelo.NotaFiscal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaFiscalDAO {
    private Connection connection;

    public NotaFiscalDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(NotaFiscal nota) {
        String sql = "INSERT INTO nota_fiscal (nome, descricao) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nota.getnome());
            stmt.setString(2, nota.getdescricao());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar nota fiscal: ", e);
        }
    }

    public NotaFiscal buscar(int id) {
        String sql = "SELECT * FROM nota_fiscal WHERE id_nota_fiscal = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                NotaFiscal nota = new NotaFiscal();
                nota.setid_nota_fiscal(rs.getInt("id_nota_fiscal"));
                nota.setnome(rs.getString("nome"));
                nota.setdescricao(rs.getString("descricao"));
                return nota;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar nota fiscal: ", e);
        }
        return null;
    }

    public void atualizar(NotaFiscal nota) {
        String sql = "UPDATE nota_fiscal SET nome=?, descricao=? WHERE id_nota_fiscal=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nota.getnome());
            stmt.setString(2, nota.getdescricao());
            stmt.setInt(3, nota.getid_nota_fiscal());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar nota fiscal: ", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM nota_fiscal WHERE id_nota_fiscal=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir nota fiscal: ", e);
        }
    }

    public List<NotaFiscal> listarTodos() {
        String sql = "SELECT * FROM nota_fiscal";
        List<NotaFiscal> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NotaFiscal nota = new NotaFiscal();
                nota.setid_nota_fiscal(rs.getInt("id_nota_fiscal"));
                nota.setnome(rs.getString("nome"));
                nota.setdescricao(rs.getString("descricao"));
                lista.add(nota);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar notas fiscais: ", e);
        }
        return lista;
    }
}
