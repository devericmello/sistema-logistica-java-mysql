package DAO;

import Factory.ConnectionFactory;
import Modelo.Questao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestaoDAO {
    private Connection conn;

    public QuestaoDAO(Connection conn) {
        this.conn = conn;
    }

   public void inserir(Questao q) throws Exception {
    String sql = "INSERT INTO questao (id_prova, numero_questao, enunciado, imagem) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, q.getIdProva());
        ps.setInt(2, q.getNumeroQuestao());
        ps.setString(3, q.getEnunciado());
        if (q.getImagem() != null) ps.setBytes(4, q.getImagem());
        else ps.setNull(4, java.sql.Types.BLOB);
        ps.executeUpdate();
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) q.setIdQuestao(rs.getInt(1));
        }
    }

    }

    public Questao buscarPorId(int idQuestao) throws SQLException {
        String sql = "SELECT * FROM questao WHERE id_questao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idQuestao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Questao questao = new Questao();
                    questao.setIdQuestao(rs.getInt("id_questao"));
                    questao.setIdProva(rs.getInt("id_prova"));
                    questao.setNumeroQuestao(rs.getInt("numero_questao"));
                    questao.setEnunciado(rs.getString("enunciado"));
                    return questao;
                }
            }
        }
        return null;
    }

    public List<Questao> listarPorProva(int idProva) throws SQLException {
        List<Questao> lista = new ArrayList<>();
        String sql = "SELECT * FROM questao WHERE id_prova = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProva);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Questao questao = new Questao();
                    questao.setIdQuestao(rs.getInt("id_questao"));
                    questao.setIdProva(rs.getInt("id_prova"));
                    questao.setNumeroQuestao(rs.getInt("numero_questao"));
                    questao.setEnunciado(rs.getString("enunciado"));
                    lista.add(questao);
                }
            }
        }
        return lista;
    }

    public void atualizar(Questao questao) throws SQLException {
        String sql = "UPDATE questao SET id_prova = ?, numero_questao = ?, enunciado = ? WHERE id_questao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questao.getIdProva());
            stmt.setInt(2, questao.getNumeroQuestao());
            stmt.setString(3, questao.getEnunciado());
            stmt.setInt(4, questao.getIdQuestao());
            stmt.executeUpdate();
        }
    }

    public void excluir(int idQuestao) throws SQLException {
        String sql = "DELETE FROM questao WHERE id_questao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idQuestao);
            stmt.executeUpdate();
        }
    }
    
    
    
    
}
