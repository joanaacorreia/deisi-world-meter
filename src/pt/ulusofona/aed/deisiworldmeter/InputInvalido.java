package ulusofona.aed.deisiworldmeter;

public class InputInvalido {
    String nome;
    int validos;
    int invalidos;
    int primeiroInvalido;

    InputInvalido(String nome, int validos, int invalidos, int primeiroInvalido) {
        this.nome = nome;
        this.validos = validos;
        this.invalidos = invalidos;
        this.primeiroInvalido = primeiroInvalido;
    }

    @Override
    public String toString() {
        return nome + " | " + validos + " | " + invalidos + " | " + primeiroInvalido;
    }
}
