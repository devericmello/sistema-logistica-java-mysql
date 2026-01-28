package Modelo;

public class EstoqueMovimentacao {
    
    private int id_estoque_movimentacao;
    private int id_estoque;
    private String quantidade;
    private String data_movimentacao;
    private String motivo;

    // Getters e Setters
    public int getid_estoque_movimentacao() {
        return id_estoque_movimentacao;
    }

    public void setid_estoque_movimentacao(int id_estoque_movimentacao) {
        this.id_estoque_movimentacao = id_estoque_movimentacao;
    }

    public int getid_estoque() {
        return id_estoque;
    }

    public void setid_estoque(int id_estoque) {
        this.id_estoque = id_estoque;
    }

    public String getquantidade() {
        return quantidade;
    }

    public void setquantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getdata_movimentacao() {
        return data_movimentacao;
    }

    public void setdata_movimentacao(String data_movimentacao) {
        this.data_movimentacao = data_movimentacao;
    }

    public String getmotivo() {
        return motivo;
    }

    public void setmotivo(String motivo) {
        this.motivo = motivo;
    }
}
