package pt.ulusofona.aed.deisiworldmeter;

public class Pais {
    String nome;
    int id;
    String alfa2;
    String alfa3;
    int nRegistosPopulacao = 0;

    Pais(String nome, int id, String alfa2, String alfa3) {
        this.nome = nome;
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
    }

    public void incrementarDadosPopulacao() {
        this.nRegistosPopulacao++;
    }

    public String getAlfa2() {
        return this.alfa2;
    }

    public int getId() {
        return this.id;
    }


    @Override
    public String toString() {
        String resultado = nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase();

        if (this.id > 700) {
            resultado += " | " + nRegistosPopulacao;
        }

        return resultado;
    }
}
