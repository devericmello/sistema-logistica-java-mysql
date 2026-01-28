package DAO;

import Factory.ConnectionFactory;
import Modelo.Alternativa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlternativaDAO {
    private Connection conn;

    public AlternativaDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Alternativa alternativa) throws SQLException {
        String sql = "INSERT INTO alternativa (id_questao, letra, texto, correta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, alternativa.getIdQuestao());
            stmt.setString(2, String.valueOf(alternativa.getLetra()));
            stmt.setString(3, alternativa.getTexto());
            stmt.setBoolean(4, alternativa.isCorreta());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    alternativa.setIdAlternativa(rs.getInt(1));
                }
            }
        }
    }

    public List<Alternativa> listarPorQuestao(int idQuestao) throws SQLException {
        List<Alternativa> lista = new ArrayList<>();
        String sql = "SELECT * FROM alternativa WHERE id_questao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idQuestao);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Alternativa alt = new Alternativa();
                    alt.setIdAlternativa(rs.getInt("id_alternativa"));
                    alt.setIdQuestao(rs.getInt("id_questao"));
                    alt.setLetra(rs.getString("letra").charAt(0));
                    alt.setTexto(rs.getString("texto"));
                    alt.setCorreta(rs.getBoolean("correta"));
                    lista.add(alt);
                }
            }
        }
        return lista;
    }

    public void atualizar(Alternativa alternativa) throws SQLException {
        String sql = "UPDATE alternativa SET id_questao = ?, letra = ?, texto = ?, correta = ? WHERE id_alternativa = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alternativa.getIdQuestao());
            stmt.setString(2, String.valueOf(alternativa.getLetra()));
            stmt.setString(3, alternativa.getTexto());
            stmt.setBoolean(4, alternativa.isCorreta());
            stmt.setInt(5, alternativa.getIdAlternativa());
            stmt.executeUpdate();
        }
    }

    public void excluir(int idAlternativa) throws SQLException {
        String sql = "DELETE FROM alternativa WHERE id_alternativa = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAlternativa);
            stmt.executeUpdate();
        }
    }
    
    public Alternativa buscar(int id_alternativa) throws SQLException {
    String sql = "SELECT * FROM alternativa WHERE id_alternativa = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id_alternativa);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Alternativa a = new Alternativa();
                a.setIdAlternativa(rs.getInt("id_alternativa"));
                a.setIdQuestao(rs.getInt("id_questao"));
                a.setLetra(rs.getString("letra").charAt(0));
                a.setTexto(rs.getString("texto"));
                a.setCorreta(rs.getBoolean("correta"));
                return a;
            }
        }
    }
    return null;
}

}
