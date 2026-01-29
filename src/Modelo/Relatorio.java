package Modelo;

public class Relatorio {
    
    private int id_relatorio;
    private String descricao;
    private String tipo_relatorio;
    private String data_criacao;

    // Getters e Setters
    public int getid_relatorio() {
        return id_relatorio;
    }

    public void setid_relatorio(int id_relatorio) {
        this.id_relatorio = id_relatorio;
    }

    public String getdescricao() {
        return descricao;
    }

    public void setdescricao(String descricao) {
        this.descricao = descricao;
    }

    public String gettipo_relatorio() {
        return tipo_relatorio;
    }

    public void settipo_relatorio(String tipo_relatorio) {
        this.tipo_relatorio = tipo_relatorio;
    }

    public String getdata_criacao() {
        return data_criacao;
    }

    public void setdata_criacao(String data_criacao) {
        this.data_criacao = data_criacao;
    }
}
