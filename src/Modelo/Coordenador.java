package Modelo;

public class Coordenador {
    
    private int id_coordenador;
    private String nome;
    private String login;
    private String senha;

    // Getters e Setters
    public int getid_coordenador() {
        return id_coordenador;
    }

    public void setid_coordenador(int id_coordenador) {
        this.id_coordenador = id_coordenador;
    }

    public String getnome() {
        return nome;
    }

    public void setnome(String nome) {
        this.nome = nome;
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
}
