package DAO;

import Factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvaliacaoProvaDAO {

    private Connection connection;

    public AvaliacaoProvaDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void inserir(int idProva, int idDesempenho) throws SQLException {
        String sql = "INSERT INTO avaliacao_prova (id_prova, id_desempenho) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProva);
            stmt.setInt(2, idDesempenho);
            stmt.executeUpdate();
        }
    }

    // READ
    public boolean existeAvaliacao(int idProva, int idDesempenho) throws SQLException {
        String sql = "SELECT * FROM avaliacao_prova WHERE id_prova = ? AND id_desempenho = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProva);
            stmt.setInt(2, idDesempenho);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    // DELETE
    public void deletar(int idProva, int idDesempenho) throws SQLException {
        String sql = "DELETE FROM avaliacao_prova WHERE id_prova = ? AND id_desempenho = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProva);
            stmt.setInt(2, idDesempenho);
            stmt.executeUpdate();
        }
    }

    // LISTAR TODOS (opcional)
    public void listarTodos() throws SQLException {
        String sql = "SELECT * FROM avaliacao_prova";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int idProva = rs.getInt("id_prova");
                int idDesempenho = rs.getInt("id_desempenho");
                System.out.println("Prova: " + idProva + ", Desempenho: " + idDesempenho);
            }
        }
    }
}
