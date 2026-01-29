package DAO;

import Factory.ConnectionFactory;
import Modelo.Coordenador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoordenadorDAO {
    private Connection connection;

    public CoordenadorDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void cadastro(Coordenador coordenador) {
        String sql = "INSERT INTO coordenador (nome, login, senha) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, coordenador.getnome());
            stmt.setString(2, coordenador.getlogin());
            stmt.setString(3, coordenador.getsenha());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar coordenador: ", e);
        }
    }

    public Coordenador buscar(String login) {
        String sql = "SELECT * FROM coordenador WHERE login = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Coordenador coordenador = new Coordenador();
                coordenador.setid_coordenador(rs.getInt("id_coordenador"));
                coordenador.setnome(rs.getString("nome"));
                coordenador.setlogin(rs.getString("login"));
                coordenador.setsenha(rs.getString("senha"));
                return coordenador;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar coordenador: ", e);
        }
        return null;
    }

    public void atualizar(Coordenador coordenador) {
        String sql = "UPDATE coordenador SET nome=?, login=?, senha=? WHERE id_coordenador=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, coordenador.getnome());
            stmt.setString(2, coordenador.getlogin());
            stmt.setString(3, coordenador.getsenha());
            stmt.setInt(4, coordenador.getid_coordenador());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar coordenador: ", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM coordenador WHERE id_coordenador=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir coordenador: ", e);
        }
    }

    public List<Coordenador> listarTodos() {
        String sql = "SELECT * FROM coordenador";
        List<Coordenador> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Coordenador coordenador = new Coordenador();
                coordenador.setid_coordenador(rs.getInt("id_coordenador"));
                coordenador.setnome(rs.getString("nome"));
                coordenador.setlogin(rs.getString("login"));
                coordenador.setsenha(rs.getString("senha"));
                lista.add(coordenador);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar coordenadores: ", e);
        }
        return lista;
    }
}
