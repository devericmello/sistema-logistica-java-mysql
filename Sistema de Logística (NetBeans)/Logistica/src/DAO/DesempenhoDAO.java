package DAO;

import Factory.ConnectionFactory;
import Modelo.Desempenho;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DesempenhoDAO {
    private Connection connection;

    public DesempenhoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(Desempenho desempenho) {
        String sql = "INSERT INTO desempenho (id_aluno, id_prova, nota_global, percentual_acerto, data_avaliacao, observacao, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, desempenho.getid_aluno());
            stmt.setInt(2, desempenho.getid_prova());
            stmt.setDouble(3, desempenho.getnota_global());
            stmt.setDouble(4, desempenho.getpercentual_acerto());
            if (desempenho.getdata_avaliacao() != null) {
                stmt.setTimestamp(5, new java.sql.Timestamp(desempenho.getdata_avaliacao().getTime()));
            } else {
                stmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            }
            stmt.setString(6, desempenho.getobservacao());
            stmt.setString(7, desempenho.getstatus());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar desempenho: ", e);
        }
    }

    public Desempenho buscar(int id_desempenho) {
        String sql = "SELECT * FROM desempenho WHERE id_desempenho = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_desempenho);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Desempenho d = new Desempenho();
                d.setid_desempenho(rs.getInt("id_desempenho"));
                d.setid_aluno(rs.getInt("id_aluno"));
                d.setid_prova(rs.getInt("id_prova"));
                d.setnota_global(rs.getDouble("nota_global"));
                d.setpercentual_acerto(rs.getDouble("percentual_acerto"));
                d.setdata_avaliacao(rs.getTimestamp("data_avaliacao"));
                d.setobservacao(rs.getString("observacao"));
                d.setstatus(rs.getString("status"));
                return d;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar desempenho: ", e);
        }
        return null;
    }

    public void excluir(int id_desempenho) {
        String sql = "DELETE FROM desempenho WHERE id_desempenho = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_desempenho);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir desempenho: ", e);
        }
    }

    public List<Desempenho> listarTodos() {
        String sql = "SELECT * FROM desempenho";
        List<Desempenho> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Desempenho d = new Desempenho();
                d.setid_desempenho(rs.getInt("id_desempenho"));
                d.setid_aluno(rs.getInt("id_aluno"));
                d.setid_prova(rs.getInt("id_prova"));
                d.setnota_global(rs.getDouble("nota_global"));
                d.setpercentual_acerto(rs.getDouble("percentual_acerto"));
                d.setdata_avaliacao(rs.getTimestamp("data_avaliacao"));
                d.setobservacao(rs.getString("observacao"));
                d.setstatus(rs.getString("status"));
                lista.add(d);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar desempenhos: ", e);
        }
        return lista;
    }

    // Método extra: Buscar desempenho por aluno e prova
    public Desempenho buscarPorAlunoEProva(int id_aluno, int id_prova) {
        String sql = "SELECT * FROM desempenho WHERE id_aluno = ? AND id_prova = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_aluno);
            stmt.setInt(2, id_prova);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Desempenho d = new Desempenho();
                d.setid_desempenho(rs.getInt("id_desempenho"));
                d.setid_aluno(rs.getInt("id_aluno"));
                d.setid_prova(rs.getInt("id_prova"));
                d.setnota_global(rs.getDouble("nota_global"));
                d.setpercentual_acerto(rs.getDouble("percentual_acerto"));
                d.setdata_avaliacao(rs.getTimestamp("data_avaliacao"));
                d.setobservacao(rs.getString("observacao"));
                d.setstatus(rs.getString("status"));
                return d;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar desempenho por aluno e prova: ", e);
        }
        return null;
    }

    // Método para listar todos desempenhos de um aluno
    public List<Desempenho> listarPorAluno(int id_aluno) {
        String sql = "SELECT * FROM desempenho WHERE id_aluno = ?";
        List<Desempenho> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_aluno);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Desempenho d = new Desempenho();
                d.setid_desempenho(rs.getInt("id_desempenho"));
                d.setid_aluno(rs.getInt("id_aluno"));
                d.setid_prova(rs.getInt("id_prova"));
                d.setnota_global(rs.getDouble("nota_global"));
                d.setpercentual_acerto(rs.getDouble("percentual_acerto"));
                d.setdata_avaliacao(rs.getTimestamp("data_avaliacao"));
                d.setobservacao(rs.getString("observacao"));
                d.setstatus(rs.getString("status"));
                lista.add(d);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar desempenhos do aluno: ", e);
        }
        return lista;
    }
}
