package Modelo;

public class ElaboraRelatorio {
    private int id_coordenador;
    private int id_relatorio;

    // Construtor vazio
    public ElaboraRelatorio() {}

    // Construtor completo (opcional)
    public ElaboraRelatorio(int id_coordenador, int id_relatorio) {
        this.id_coordenador = id_coordenador;
        this.id_relatorio = id_relatorio;
    }

    // Getters e Setters

    public int getid_coordenador() {
        return id_coordenador;
    }

    public void setid_coordenador(int id_coordenador) {
        this.id_coordenador = id_coordenador;
    }

    public int getid_relatorio() {
        return id_relatorio;
    }

    public void setid_relatorio(int id_relatorio) {
        this.id_relatorio = id_relatorio;
    }
}
