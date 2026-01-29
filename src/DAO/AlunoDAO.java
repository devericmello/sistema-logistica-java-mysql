package DAO;

import Factory.ConnectionFactory;
import Modelo.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AlunoDAO {

    private final Connection connection;

    public AlunoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    // Método para adicionar um aluno no banco
   public void adiciona(Aluno aluno) {
    String sql = "INSERT INTO aluno(nome_completo, data_nascimento, sexo, telefone, email, turma, numero, bairro, cep, rua, nacionalidade, cidade, UF, senha, foto_perfil) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, aluno.getnome_completo());
        stmt.setString(2, aluno.getdata_nascimento());
        stmt.setString(3, aluno.getsexo());
        stmt.setString(4, aluno.gettelefone());
        stmt.setString(5, aluno.getemail());
        stmt.setString(6, aluno.getturma());
        stmt.setString(7, aluno.getnumero());
        stmt.setString(8, aluno.getbairro());
        stmt.setString(9, aluno.getcep());
        stmt.setString(10, aluno.getrua());
        stmt.setString(11, aluno.getnacionalidade());
        stmt.setString(12, aluno.getcidade());
        stmt.setString(13, aluno.getUF());
        stmt.setString(14, aluno.getSenha());
        stmt.setString(15, aluno.getFotoPerfil()); // NOVO

        stmt.execute();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao salvar o usuário: " + e.getMessage());
    }
}


    // Método para recuperar um aluno pelo ID
    public Aluno Recupera(Aluno aluno) {
        Aluno retorno = new Aluno();

        String sql = "SELECT id_aluno, nome_completo, data_nascimento, sexo, "
                + "telefone, email, turma, numero, bairro, cep, rua, "
                + "nacionalidade, cidade, UF, senha, foto_perfil "
                + "FROM aluno WHERE id_aluno = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, aluno.getid_aluno());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                retorno.setid_aluno(rs.getLong("id_aluno"));
                retorno.setnome_completo(rs.getString("nome_completo"));
                retorno.setdata_nascimento(rs.getString("data_nascimento"));
                retorno.setsexo(rs.getString("sexo"));
                retorno.settelefone(rs.getString("telefone"));
                retorno.setemail(rs.getString("email"));
                retorno.setturma(rs.getString("turma"));
                retorno.setnumero(rs.getString("numero"));
                retorno.setbairro(rs.getString("bairro"));
                retorno.setcep(rs.getString("cep"));
                retorno.setrua(rs.getString("rua"));
                retorno.setnacionalidade(rs.getString("nacionalidade"));
                retorno.setcidade(rs.getString("cidade"));
                retorno.setUF(rs.getString("UF"));
                retorno.setSenha(rs.getString("senha")); 
                retorno.setFotoPerfil(rs.getString("foto_perfil")); 
            } else {
                retorno = null;
            }

            rs.close();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return retorno;
    }

    // Método para excluir um aluno do banco
    public void excluirAluno(Long id_aluno) {
        String sql = "DELETE FROM aluno WHERE id_aluno = ?";

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id_aluno);
            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new SQLException("Nenhum aluno encontrado com id: " + id_aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir aluno", e);
        }
    }

    // Método para alterar as informações de um aluno, incluindo a senha
    public void alterar(Aluno aluno) {
    try {
        // Busca o aluno original no banco, pelo id
        Aluno original = Recupera(aluno); // Usa seu método já existente

        // Se o aluno não for encontrado, lança exceção
        if (original == null) {
            JOptionPane.showMessageDialog(null, "Aluno não encontrado para atualizar.");
            return;
        }

        // Monta a SQL dinamicamente (aqui um exemplo simples com os campos mais usados)
        String sql = "UPDATE aluno SET "
    + "nome_completo = ?, email = ?, telefone = ?, senha = ?, bairro = ?, rua = ?, numero = ?, cep = ? "
    + "WHERE id_aluno = ?";

           PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setString(1, aluno.getnome_completo() != null && !aluno.getnome_completo().isEmpty() ? aluno.getnome_completo() : original.getnome_completo());
           stmt.setString(2, aluno.getemail() != null && !aluno.getemail().isEmpty() ? aluno.getemail() : original.getemail());
           stmt.setString(3, aluno.gettelefone() != null && !aluno.gettelefone().isEmpty() ? aluno.gettelefone() : original.gettelefone());
           stmt.setString(4, aluno.getSenha() != null && !aluno.getSenha().isEmpty() ? aluno.getSenha() : original.getSenha());
           stmt.setString(5, aluno.getbairro() != null && !aluno.getbairro().isEmpty() ? aluno.getbairro() : original.getbairro());
           stmt.setString(6, aluno.getrua() != null && !aluno.getrua().isEmpty() ? aluno.getrua() : original.getrua());
           stmt.setString(7, aluno.getnumero() != null && !aluno.getnumero().isEmpty() ? aluno.getnumero() : original.getnumero());
           stmt.setString(8, aluno.getcep() != null && !aluno.getcep().isEmpty() ? aluno.getcep() : original.getcep());
           stmt.setLong(9, aluno.getid_aluno());


        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Aluno atualizado com sucesso!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar o cadastro do Aluno: " + e.getMessage());
    }
}

    // Método para recuperar um aluno pelo nome
    public Aluno RecuperaPorNome(String nome_completo) {
        Aluno retorno = null;

        String sql = "SELECT * FROM aluno WHERE nome_completo = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome_completo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                retorno = new Aluno();
                retorno.setid_aluno(rs.getLong("id_aluno"));
                retorno.setnome_completo(rs.getString("nome_completo"));
                retorno.setdata_nascimento(rs.getString("data_nascimento"));
                retorno.setsexo(rs.getString("sexo"));
                retorno.settelefone(rs.getString("telefone"));
                retorno.setemail(rs.getString("email"));
                retorno.setturma(rs.getString("turma"));
                retorno.setnumero(rs.getString("numero"));
                retorno.setbairro(rs.getString("bairro"));
                retorno.setcep(rs.getString("cep"));
                retorno.setrua(rs.getString("rua"));
                retorno.setnacionalidade(rs.getString("nacionalidade"));
                retorno.setcidade(rs.getString("cidade"));
                retorno.setUF(rs.getString("UF"));
                retorno.setSenha(rs.getString("senha"));  // Recuperando a senha
                retorno.setFotoPerfil(rs.getString("foto_perfil")); // Adicione após setSenha()
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Aluno por nome", e);
        }

        return retorno;
    }
    
    // Método alternativo para recuperar aluno pelo id diretamente
public Aluno RecuperaPorId(long id_aluno) {
    Aluno retorno = null;
    String sql = "SELECT id_aluno, nome_completo, data_nascimento, sexo, telefone, email, turma, numero, bairro, cep, rua, nacionalidade, cidade, UF, senha, foto_perfil FROM aluno WHERE id_aluno = ?";
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id_aluno);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            retorno = new Aluno();
            retorno.setid_aluno(rs.getLong("id_aluno"));
            retorno.setnome_completo(rs.getString("nome_completo"));
            retorno.setdata_nascimento(rs.getString("data_nascimento"));
            retorno.setsexo(rs.getString("sexo"));
            retorno.settelefone(rs.getString("telefone"));
            retorno.setemail(rs.getString("email"));
            retorno.setturma(rs.getString("turma"));
            retorno.setnumero(rs.getString("numero"));
            retorno.setbairro(rs.getString("bairro"));
            retorno.setcep(rs.getString("cep"));
            retorno.setrua(rs.getString("rua"));
            retorno.setnacionalidade(rs.getString("nacionalidade"));
            retorno.setcidade(rs.getString("cidade"));
            retorno.setUF(rs.getString("UF"));
            retorno.setSenha(rs.getString("senha"));
            retorno.setFotoPerfil(rs.getString("foto_perfil")); 
        }
        rs.close();
        stmt.close();
    } catch (SQLException u) {
        throw new RuntimeException(u);
    }
    return retorno;
}

public void atualizarFotoPerfil(long id_aluno, String caminhoFoto) {
    String sql = "UPDATE aluno SET foto_perfil = ? WHERE id_aluno = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, caminhoFoto);
        stmt.setLong(2, id_aluno);
        stmt.executeUpdate();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar foto de perfil: " + e.getMessage());
    }
}

public java.util.List<Modelo.Aluno> listarTodos() {
    java.util.List<Modelo.Aluno> lista = new java.util.ArrayList<>();

    final String sql =
        "SELECT id_aluno, nome_completo, " +
        "       DATE_FORMAT(data_nascimento,'%Y-%m-%d') AS data_nascimento, " +
        "       sexo, telefone, email, turma, numero, bairro, cep, rua, " +
        "       nacionalidade, cidade, UF " +
        "FROM aluno ORDER BY id_aluno";

    try (java.sql.PreparedStatement st = connection.prepareStatement(sql);
         java.sql.ResultSet rs = st.executeQuery()) {

        while (rs.next()) {
            Modelo.Aluno a = new Modelo.Aluno();
            a.setid_aluno(rs.getLong("id_aluno"));
            a.setnome_completo(rs.getString("nome_completo"));
            a.setdata_nascimento(rs.getString("data_nascimento")); // yyyy-MM-dd
            a.setsexo(rs.getString("sexo"));
            a.settelefone(rs.getString("telefone"));
            a.setemail(rs.getString("email"));
            a.setturma(rs.getString("turma"));
            a.setnumero(rs.getString("numero"));
            a.setbairro(rs.getString("bairro"));
            a.setcep(rs.getString("cep"));
            a.setrua(rs.getString("rua"));
            a.setnacionalidade(rs.getString("nacionalidade"));
            a.setcidade(rs.getString("cidade"));
            a.setUF(rs.getString("UF"));
            lista.add(a);
        }
    } catch (java.sql.SQLException e) {
        throw new RuntimeException("Erro ao listar alunos", e);
    }
    return lista;
}

}
