package Modelo;

public class Prova {
    
    private int id_prova;
    private String descricao;
    private String data_aplicacao;
    private int id_instrutor;

    // Getters e Setters
    public int getid_prova() {
        return id_prova;
    }

    public void setid_prova(int id_prova) {
        this.id_prova = id_prova;
    }

    public String getdescricao() {
        return descricao;
    }

    public void setdescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getdata_aplicacao() {
        return data_aplicacao;
    }

    public void setdata_aplicacao(String data_aplicacao) {
        this.data_aplicacao = data_aplicacao;
    }

    public int getid_instrutor() {
        return id_instrutor;
    }

    public void setid_instrutor(int id_instrutor) {
        this.id_instrutor = id_instrutor;
    }
}
