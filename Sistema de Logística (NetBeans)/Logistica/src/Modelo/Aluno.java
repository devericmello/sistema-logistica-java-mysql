package Modelo;

public class Aluno {
    private Long id_aluno;
    private String nome_completo;
    private String data_nascimento;
    private String sexo;
    private String telefone;
    private String email;
    private String turma;
    private String numero;
    private String bairro;
    private String cep;
    private String rua;
    private String nacionalidade;
    private String cidade;
    private String UF;
    private String senha;  // Novo atributo
    private String fotoPerfil;

    // Getters e Setters
    public Long getid_aluno() {
        return id_aluno;
    }

    public void setid_aluno(Long id_aluno) {
        this.id_aluno = id_aluno;
    }

    public String getnome_completo() {
        return nome_completo;
    }

    public void setnome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
    }

    public String getdata_nascimento() {
        return data_nascimento;
    }

    public void setdata_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getsexo() {
        return sexo;
    }

    public void setsexo(String sexo) {
        this.sexo = sexo;
    }

    public String gettelefone() {
        return telefone;
    }

    public void settelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getturma() {
        return turma;
    }

    public void setturma(String turma) {
        this.turma = turma;
    }

    public String getnumero() {
        return numero;
    }

    public void setnumero(String numero) {
        this.numero = numero;
    }

    public String getbairro() {
        return bairro;
    }

    public void setbairro(String bairro) {
        this.bairro = bairro;
    }

    public String getcep() {
        return cep;
    }

    public void setcep(String cep) {
        this.cep = cep;
    }

    public String getrua() {
        return rua;
    }

    public void setrua(String rua) {
        this.rua = rua;
    }

    public String getnacionalidade() {
        return nacionalidade;
    }

    public void setnacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getcidade() {
        return cidade;
    }

    public void setcidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    // Getter e Setter para a senha
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getFotoPerfil() {
    return fotoPerfil;
}

public void setFotoPerfil(String fotoPerfil) {
    this.fotoPerfil = fotoPerfil;
}
}
