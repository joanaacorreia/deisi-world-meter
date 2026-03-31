package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Pais> paises = new ArrayList<>();
    static ArrayList<Cidade> cidades = new ArrayList<>();
    static ArrayList<Populacao> populacoes = new ArrayList<>();
    static ArrayList<InputInvalido> relatorio = new ArrayList<>();

    public static ArrayList<?> getObjects(TipoEntidade tipo) {
        return switch (tipo) {
            case PAIS -> paises;
            case CIDADE -> cidades;
            case INPUT_INVALIDO -> relatorio;
            default -> new ArrayList<>();
        };
    }

    public static boolean parseFiles(File file) {
        return true;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("==========================================");
            System.out.println("            DEISI WORLD METER             ");
            System.out.println("==========================================");
            System.out.println("[1] Carregar Ficheiros");
            System.out.println("[2] Listar Países");
            System.out.println("[3] Listar Cidades");
            System.out.println("[4] Relatório de Erros");
            System.out.println("[5] Sair");
            System.out.println("------------------------------------------");
            System.out.print("Seleção > ");

            String opcao = input.nextLine();

            if (opcao.equals("5")) {
                break;
            }

        }

        input.close();
    }
}
