package Modelo;

public class Questao {
    private int idQuestao;
    private int idProva;
    private int numeroQuestao;
    private String enunciado;
    private byte[] imagem;

    // Getters e Setters
    public int getIdQuestao() { return idQuestao; }
    public void setIdQuestao(int idQuestao) { this.idQuestao = idQuestao; }

    public int getIdProva() { return idProva; }
    public void setIdProva(int idProva) { this.idProva = idProva; }

    public int getNumeroQuestao() { return numeroQuestao; }
    public void setNumeroQuestao(int numeroQuestao) { this.numeroQuestao = numeroQuestao; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    
    public byte[] getImagem() { return imagem; }
public void setImagem(byte[] imagem) { this.imagem = imagem; }
}
