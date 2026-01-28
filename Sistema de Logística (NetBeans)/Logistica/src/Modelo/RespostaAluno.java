package Modelo;

import java.util.Date;

public class RespostaAluno {
    private int idResposta;
    private int idAluno;
    private int idQuestao;
    private int idAlternativaEscolhida;
    private Date dataResposta;

    // Getters e Setters
    public int getIdResposta() { return idResposta; }
    public void setIdResposta(int idResposta) { this.idResposta = idResposta; }

    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }

    public int getIdQuestao() { return idQuestao; }
    public void setIdQuestao(int idQuestao) { this.idQuestao = idQuestao; }

    public int getIdAlternativaEscolhida() { return idAlternativaEscolhida; }
    public void setIdAlternativaEscolhida(int idAlternativaEscolhida) { this.idAlternativaEscolhida = idAlternativaEscolhida; }

    public Date getDataResposta() { return dataResposta; }
    public void setDataResposta(Date dataResposta) { this.dataResposta = dataResposta; }
}
