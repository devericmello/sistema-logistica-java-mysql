package DAO;

import Factory.ConnectionFactory;
import Modelo.NotaFiscalMaterial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaFiscalMaterialDAO {
    private Connection connection;

    public NotaFiscalMaterialDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(NotaFiscalMaterial nfm) {
        String sql = "INSERT INTO nota_fiscal_material (id_nota_fiscal, id_material) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, nfm.getid_nota_fiscal());
            stmt.setInt(2, nfm.getid_material());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar relação nota fiscal/material: ", e);
        }
    }

    public NotaFiscalMaterial buscar(int idNotaFiscal, int idMaterial) {
        String sql = "SELECT * FROM nota_fiscal_material WHERE id_nota_fiscal = ? AND id_material = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idNotaFiscal);
            stmt.setInt(2, idMaterial);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                NotaFiscalMaterial nfm = new NotaFiscalMaterial();
                nfm.setid_nota_fiscal(rs.getInt("id_nota_fiscal"));
                nfm.setid_material(rs.getInt("id_material"));
                return nfm;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relação nota fiscal/material: ", e);
        }
        return null;
    }

    public void excluir(int idNotaFiscal, int idMaterial) {
        String sql = "DELETE FROM nota_fiscal_material WHERE id_nota_fiscal = ? AND id_material = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idNotaFiscal);
            stmt.setInt(2, idMaterial);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir relação nota fiscal/material: ", e);
        }
    }

    public List<NotaFiscalMaterial> listarTodos() {
        String sql = "SELECT * FROM nota_fiscal_material";
        List<NotaFiscalMaterial> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NotaFiscalMaterial nfm = new NotaFiscalMaterial();
                nfm.setid_nota_fiscal(rs.getInt("id_nota_fiscal"));
                nfm.setid_material(rs.getInt("id_material"));
                lista.add(nfm);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar relações nota fiscal/material: ", e);
        }
        return lista;
    }
}
