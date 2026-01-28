package Modelo;

public class AvaliacaoProva {
    private int id_prova;
    private int id_desempenho;

    public AvaliacaoProva() {}

    public AvaliacaoProva(int id_prova, int id_desempenho) {
        this.id_prova = id_prova;
        this.id_desempenho = id_desempenho;
    }

    public int getid_prova() {
        return id_prova;
    }

    public void setid_prova(int id_prova) {
        this.id_prova = id_prova;
    }

    public int getid_desempenho() {
        return id_desempenho;
    }

    public void setid_desempenho(int id_desempenho) {
        this.id_desempenho = id_desempenho;
    }
}
