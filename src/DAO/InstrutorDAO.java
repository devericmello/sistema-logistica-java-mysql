package DAO;

import Factory.ConnectionFactory;
import Modelo.Instrutor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrutorDAO {
    private Connection connection;

    public InstrutorDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    // Cadastro já com foto
    public void cadastro(Instrutor instrutor) {
        String sql = "INSERT INTO instrutor (nome, sobrenome, login, senha, foto_perfil) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, instrutor.getnome());
            stmt.setString(2, instrutor.getsobrenome());
            stmt.setString(3, instrutor.getlogin());
            stmt.setString(4, instrutor.getsenha());
            stmt.setString(5, instrutor.getfotoPerfil()); // Caminho/nome da foto
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar instrutor: ", e);
        }
    }

    // Buscar pelo login (busca foto também)
    public Instrutor buscar(String login) {
        String sql = "SELECT * FROM instrutor WHERE login = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Instrutor instrutor = new Instrutor();
                instrutor.setid_instrutor(rs.getInt("id_instrutor"));
                instrutor.setnome(rs.getString("nome"));
                instrutor.setsobrenome(rs.getString("sobrenome"));
                instrutor.setlogin(rs.getString("login"));
                instrutor.setsenha(rs.getString("senha"));
                instrutor.setfotoPerfil(rs.getString("foto_perfil")); // <-- Novo campo
                rs.close();
                stmt.close();
                return instrutor;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar instrutor: ", e);
        }
        return null;
    }

    // Atualizar (inclusive foto, se mudar)
    public void atualizar(Instrutor instrutor) {
        String sql = "UPDATE instrutor SET nome=?, sobrenome=?, login=?, senha=?, foto_perfil=? WHERE id_instrutor=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, instrutor.getnome());
            stmt.setString(2, instrutor.getsobrenome());
            stmt.setString(3, instrutor.getlogin());
            stmt.setString(4, instrutor.getsenha());
            stmt.setString(5, instrutor.getfotoPerfil());
            stmt.setInt(6, instrutor.getid_instrutor());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar instrutor: ", e);
        }
    }

    // Excluir
    public void excluir(int id) {
        String sql = "DELETE FROM instrutor WHERE id_instrutor=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir instrutor: ", e);
        }
    }
    
    public void atualizarFotoPerfil(int id_instrutor, String caminhoFoto) {
    String sql = "UPDATE instrutor SET foto_perfil = ? WHERE id_instrutor = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, caminhoFoto);
        stmt.setInt(2, id_instrutor);
        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao atualizar foto de perfil", e);
    }
}

}