package Modelo;

import java.util.Date;

public class Desempenho {
    private int id_desempenho;
    private int id_aluno;
    private int id_prova;
    private double nota_global;
    private double percentual_acerto;
    private Date data_avaliacao;
    private String observacao;
    private String status;

    // Getters e Setters
    public int getid_desempenho() { return id_desempenho; }
    public void setid_desempenho(int id_desempenho) { this.id_desempenho = id_desempenho; }

    public int getid_aluno() { return id_aluno; }
    public void setid_aluno(int id_aluno) { this.id_aluno = id_aluno; }

    public int getid_prova() { return id_prova; }
    public void setid_prova(int id_prova) { this.id_prova = id_prova; }

    public double getnota_global() { return nota_global; }
    public void setnota_global(double nota_global) { this.nota_global = nota_global; }

    public double getpercentual_acerto() { return percentual_acerto; }
    public void setpercentual_acerto(double percentual_acerto) { this.percentual_acerto = percentual_acerto; }

    public Date getdata_avaliacao() { return data_avaliacao; }
    public void setdata_avaliacao(Date data_avaliacao) { this.data_avaliacao = data_avaliacao; }

    public String getobservacao() { return observacao; }
    public void setobservacao(String observacao) { this.observacao = observacao; }

    public String getstatus() { return status; }
    public void setstatus(String status) { this.status = status; }
}
