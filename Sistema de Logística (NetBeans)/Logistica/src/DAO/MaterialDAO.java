package DAO;

import Factory.ConnectionFactory;
import Modelo.Material;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {
    private Connection connection;

    public MaterialDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(Material material) {
        String sql = "INSERT INTO material (nome, valor_total) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, material.getnome());
            stmt.setString(2, material.getvalor_total());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar material: ", e);
        }
    }

public Material buscar(String nome) {
    String sql = "SELECT * FROM material WHERE nome = ?";
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nome);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Material material = new Material();
            material.setid_material(rs.getInt("id_material"));
            material.setnome(rs.getString("nome"));
            material.setvalor_total(rs.getString("valor_total"));
            return material;
        }
        rs.close();
        stmt.close();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar material: ", e);
    }
    return null;
}

    public void atualizar(Material material) {
        String sql = "UPDATE material SET nome=?, valor_total=? WHERE id_material=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, material.getnome());
            stmt.setString(2, material.getvalor_total());
            stmt.setInt(3, material.getid_material());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar material: ", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM material WHERE id_material=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir material: ", e);
        }
    }
}
