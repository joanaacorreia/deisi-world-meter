package pt.ulusofona.aed.deisiworldmeter;

public class Cidade {
    String alfa2;
    String nome;
    String regiao;
    int populacao;
    double latitude;
    double longitude;

    Cidade(String alfa2, String nome, String regiao, int populacao, double latitude, double longitude) {
        this.alfa2 = alfa2;
        this.nome = nome;
        this.regiao = regiao;
        this.populacao = populacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format("%-5s | %-20s | %-10s | %-10d | (%.4f, %.4f)",
                alfa2.toUpperCase(), nome, regiao, populacao, latitude, longitude);
    }
}
