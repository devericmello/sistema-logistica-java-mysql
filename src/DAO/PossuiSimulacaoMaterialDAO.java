package DAO;

import Factory.ConnectionFactory;
import Modelo.PossuiSimulacaoMaterial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PossuiSimulacaoMaterialDAO {
    private Connection connection;

    public PossuiSimulacaoMaterialDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(PossuiSimulacaoMaterial psm) {
        String sql = "INSERT INTO possui_simulacao_material (id_simulacao, id_material) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, psm.getid_simulacao());
            stmt.setInt(2, psm.getid_material());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar relação simulacao/material: ", e);
        }
    }

    public PossuiSimulacaoMaterial buscar(int idSimulacao, int idMaterial) {
        String sql = "SELECT * FROM possui_simulacao_material WHERE id_simulacao = ? AND id_material = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idSimulacao);
            stmt.setInt(2, idMaterial);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PossuiSimulacaoMaterial psm = new PossuiSimulacaoMaterial();
                psm.setid_simulacao(rs.getInt("id_simulacao"));
                psm.setid_material(rs.getInt("id_material"));
                return psm;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relação simulacao/material: ", e);
        }
        return null;
    }

    public void excluir(int idSimulacao, int idMaterial) {
        String sql = "DELETE FROM possui_simulacao_material WHERE id_simulacao = ? AND id_material = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idSimulacao);
            stmt.setInt(2, idMaterial);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir relação simulacao/material: ", e);
        }
    }

    public List<PossuiSimulacaoMaterial> listarTodos() {
        String sql = "SELECT * FROM possui_simulacao_material";
        List<PossuiSimulacaoMaterial> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PossuiSimulacaoMaterial psm = new PossuiSimulacaoMaterial();
                psm.setid_simulacao(rs.getInt("id_simulacao"));
                psm.setid_material(rs.getInt("id_material"));
                lista.add(psm);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar relações simulacao/material: ", e);
        }
        return lista;
    }
}
