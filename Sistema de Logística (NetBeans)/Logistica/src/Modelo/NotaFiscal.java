package Modelo;

public class NotaFiscal {
    
    private int id_nota_fiscal;
    private String nome;
    private String descricao;

    // Getters e Setters
    public int getid_nota_fiscal() {
        return id_nota_fiscal;
    }

    public void setid_nota_fiscal(int id_nota_fiscal) {
        this.id_nota_fiscal = id_nota_fiscal;
    }

    public String getnome() {
        return nome;
    }

    public void setnome(String nome) {
        this.nome = nome;
    }

    public String getdescricao() {
        return descricao;
    }

    public void setdescricao(String descricao) {
        this.descricao = descricao;
    }
}
