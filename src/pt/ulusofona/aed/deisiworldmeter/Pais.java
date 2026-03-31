package pt.ulusofona.aed.deisiworldmeter;

public class Pais {
    String nome;
    int id;
    String alfa2;
    String alfa3;

    Pais(String nome, int id, String alfa2, String alfa3) {
        this.nome = nome;
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
    }

    @Override
    public String toString() {
        return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase();
    }
}
