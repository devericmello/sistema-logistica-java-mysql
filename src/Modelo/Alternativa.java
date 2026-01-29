package Modelo;

public class Alternativa {
    private int idAlternativa;
    private int idQuestao;
    private char letra;
    private String texto;
    private boolean correta;

    // Getters e Setters
    public int getIdAlternativa() { return idAlternativa; }
    public void setIdAlternativa(int idAlternativa) { this.idAlternativa = idAlternativa; }

    public int getIdQuestao() { return idQuestao; }
    public void setIdQuestao(int idQuestao) { this.idQuestao = idQuestao; }

    public char getLetra() { return letra; }
    public void setLetra(char letra) { this.letra = letra; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public boolean isCorreta() { return correta; }
    public void setCorreta(boolean correta) { this.correta = correta; }
}
