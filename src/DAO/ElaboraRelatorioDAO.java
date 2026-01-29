package DAO;

import Factory.ConnectionFactory;
import Modelo.ElaboraRelatorio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElaboraRelatorioDAO {
    private Connection connection;

    public ElaboraRelatorioDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(ElaboraRelatorio er) {
        String sql = "INSERT INTO elabora_relatorio (id_coordenador, id_relatorio) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, er.getid_coordenador());
            stmt.setInt(2, er.getid_relatorio());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar elabora_relatorio: ", e);
        }
    }

    public ElaboraRelatorio buscar(int idCoordenador, int idRelatorio) {
        String sql = "SELECT * FROM elabora_relatorio WHERE id_coordenador = ? AND id_relatorio = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idCoordenador);
            stmt.setInt(2, idRelatorio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ElaboraRelatorio er = new ElaboraRelatorio();
                er.setid_coordenador(rs.getInt("id_coordenador"));
                er.setid_relatorio(rs.getInt("id_relatorio"));
                return er;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar elabora_relatorio: ", e);
        }
        return null;
    }

    public void excluir(int idCoordenador, int idRelatorio) {
        String sql = "DELETE FROM elabora_relatorio WHERE id_coordenador = ? AND id_relatorio = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idCoordenador);
            stmt.setInt(2, idRelatorio);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir elabora_relatorio: ", e);
        }
    }

    public List<ElaboraRelatorio> listarTodos() {
        String sql = "SELECT * FROM elabora_relatorio";
        List<ElaboraRelatorio> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ElaboraRelatorio er = new ElaboraRelatorio();
                er.setid_coordenador(rs.getInt("id_coordenador"));
                er.setid_relatorio(rs.getInt("id_relatorio"));
                lista.add(er);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar elabora_relatorio: ", e);
        }
        return lista;
    }
}
