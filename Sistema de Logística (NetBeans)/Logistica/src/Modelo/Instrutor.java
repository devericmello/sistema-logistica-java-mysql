package Modelo;

public class Instrutor {
    
    private int id_instrutor;
    private String nome;
    private String sobrenome;
    private String login;
    private String senha;
    private String fotoPerfil;

    // Getters e Setters
    public int getid_instrutor() {
        return id_instrutor;
    }

    public void setid_instrutor(int id_instrutor) {
        this.id_instrutor = id_instrutor;
    }

    public String getnome() {
        return nome;
    }

    public void setnome(String nome) {
        this.nome = nome;
    }

    public String getsobrenome() {
        return sobrenome;
    }

    public void setsobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getlogin() {
        return login;
    }

    public void setlogin(String login) {
        this.login = login;
    }

    public String getsenha() {
        return senha;
    }

    public void setsenha(String senha) {
        this.senha = senha;
    }
    
    public String getfotoPerfil () {
        return fotoPerfil;
        }
    
    public void setfotoPerfil (String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
        
        }
}
