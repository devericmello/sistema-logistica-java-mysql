package Modelo;

public class Material {
    
    private int id_material;
    private String nome;
    private String valor_total;

    // Getters e Setters
    public int getid_material() {
        return id_material;
    }

    public void setid_material(int id_material) {
        this.id_material = id_material;
    }

    public String getnome() {
        return nome;
    }

    public void setnome(String nome) {
        this.nome = nome;
    }

    public String getvalor_total() {
        return valor_total;
    }

    public void setvalor_total(String valor_total) {
        this.valor_total = valor_total;
    }
}
