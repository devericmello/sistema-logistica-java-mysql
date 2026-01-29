package Modelo;

public class Estoque {
    
    private int id_estoque;
    private String descricao;
    private String localizacao;

    // Getters e Setters
    public int getid_estoque() {
        return id_estoque;
    }

    public void setid_estoque(int id_estoque) {
        this.id_estoque = id_estoque;
    }

    public String getdescricao() {
        return descricao;
    }

    public void setdescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getlocalizacao() {
        return localizacao;
    }

    public void setlocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
