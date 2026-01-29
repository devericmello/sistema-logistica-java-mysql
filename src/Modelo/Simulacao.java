package Modelo;

public class Simulacao {
    private int id_simulacao;
    private String descricao;
    private String objetivo;

    // Construtor vazio
    public Simulacao() {}

    // Construtor completo (opcional)
    public Simulacao(int id_simulacao, String descricao, String objetivo) {
        this.id_simulacao = id_simulacao;
        this.descricao = descricao;
        this.objetivo = objetivo;
    }

    // Getters e Setters

    public int getid_simulacao() {
        return id_simulacao;
    }

    public void setid_simulacao(int id_simulacao) {
        this.id_simulacao = id_simulacao;
    }

    public String getdescricao() {
        return descricao;
    }

    public void setdescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getobjetivo() {
        return objetivo;
    }

    public void setobjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
}
