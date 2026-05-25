package pt.ulusofona.aed.deisiworldmeter;

public class Result {
    // success -> deve retornar true ou false dependendo do que acontece com o comando
    // error -> deve retornar null se não houver erro e retornar "Comando invalido" se houver erro
    // result -> retorna uma string com o resultado do comando ou null
    boolean success;
    String error;
    String result;

    Result(boolean success, String error, String result){
        this.success = success;
        this.error = error;
        this.result = result;
    }
}
