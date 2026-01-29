package DAO;

import Modelo.RespostaAluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RespostaAlunoDAO {
    private Connection conn;

    public RespostaAlunoDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(RespostaAluno resposta) throws SQLException {
        String sql = "INSERT INTO resposta_aluno (id_aluno, id_questao, id_alternativa_escolhida, data_resposta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, resposta.getIdAluno());
            stmt.setInt(2, resposta.getIdQuestao());
            stmt.setInt(3, resposta.getIdAlternativaEscolhida());
            stmt.setTimestamp(4, new Timestamp(resposta.getDataResposta().getTime()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    resposta.setIdResposta(rs.getInt(1));
                }
            }
        }
    }

    public List<RespostaAluno> listarPorAluno(int idAluno) throws SQLException {
        List<RespostaAluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM resposta_aluno WHERE id_aluno = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RespostaAluno r = new RespostaAluno();
                    r.setIdResposta(rs.getInt("id_resposta"));
                    r.setIdAluno(rs.getInt("id_aluno"));
                    r.setIdQuestao(rs.getInt("id_questao"));
                    r.setIdAlternativaEscolhida(rs.getInt("id_alternativa_escolhida"));
                    r.setDataResposta(new Date(rs.getTimestamp("data_resposta").getTime()));
                    lista.add(r);
                }
            }
        }
        return lista;
    }

    public void atualizar(RespostaAluno resposta) throws SQLException {
        String sql = "UPDATE resposta_aluno SET id_aluno = ?, id_questao = ?, id_alternativa_escolhida = ?, data_resposta = ? WHERE id_resposta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, resposta.getIdAluno());
            stmt.setInt(2, resposta.getIdQuestao());
            stmt.setInt(3, resposta.getIdAlternativaEscolhida());
            stmt.setTimestamp(4, new Timestamp(resposta.getDataResposta().getTime()));
            stmt.setInt(5, resposta.getIdResposta());
            stmt.executeUpdate();
        }
    }

    public void excluir(int idResposta) throws SQLException {
        String sql = "DELETE FROM resposta_aluno WHERE id_resposta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idResposta);
            stmt.executeUpdate();
        }
    }
    public List<RespostaAluno> listarPorAlunoEProva(int idAluno, int idProva) throws SQLException {
    List<RespostaAluno> lista = new ArrayList<>();
    String sql = "SELECT ra.* FROM resposta_aluno ra " +
                 "JOIN questao q ON ra.id_questao = q.id_questao " +
                 "WHERE ra.id_aluno = ? AND q.id_prova = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idAluno);
        stmt.setInt(2, idProva);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                RespostaAluno r = new RespostaAluno();
                r.setIdResposta(rs.getInt("id_resposta"));
                r.setIdAluno(rs.getInt("id_aluno"));
                r.setIdQuestao(rs.getInt("id_questao"));
                r.setIdAlternativaEscolhida(rs.getInt("id_alternativa_escolhida"));
                r.setDataResposta(rs.getTimestamp("data_resposta"));
                lista.add(r);
            }
        }
    }
    return lista;
}
}
