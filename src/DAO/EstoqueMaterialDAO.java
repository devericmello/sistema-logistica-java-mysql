package DAO;

import Factory.ConnectionFactory;
import java.sql.*;

public class EstoqueMaterialDAO {
    private Connection connection;

    public EstoqueMaterialDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(int idMaterial, int idEstoque) {
        String sql = "INSERT INTO estoque_material (id_material, id_estoque) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idMaterial);
            stmt.setInt(2, idEstoque);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar estoque_material: ", e);
        }
    }

    public void excluir(int idMaterial, int idEstoque) {
        String sql = "DELETE FROM estoque_material WHERE id_material = ? AND id_estoque = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idMaterial);
            stmt.setInt(2, idEstoque);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir estoque_material: ", e);
        }
    }
}
