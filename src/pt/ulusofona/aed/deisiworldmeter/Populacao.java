package pt.ulusofona.aed.deisiworldmeter;

public class Populacao {
    int id;
    String ano;
    long masculina;
    long feminina;
    double densidade;

    Populacao(int id, String ano, long masculina, long feminina, double densidade) {
        this.id = id;
        this.ano = ano;
        this.masculina = masculina;
        this.feminina = feminina;
        this.densidade = densidade;
    }

    @Override
    public String toString() {
        return id + " | " + ano + " | " + masculina + " | " + feminina + " | " + densidade ;
    }
}
