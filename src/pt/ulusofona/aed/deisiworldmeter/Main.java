package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // ---------------------------------------------------------
    // DATA STORAGE
    // ---------------------------------------------------------
    static ArrayList<Pais> paises = new ArrayList<>();
    static ArrayList<Cidade> cidades = new ArrayList<>();
    static ArrayList<Populacao> populacoes = new ArrayList<>();
    static ArrayList<InputInvalido> relatorio = new ArrayList<>();

    public static ArrayList<?> getObjects(TipoEntidade type) {
        return switch (type) {
            case PAIS -> paises;
            case CIDADE -> cidades;
            case INPUT_INVALIDO -> relatorio;
            default -> new ArrayList<>();
        };
    }

    // ---------------------------------------------------------
    // FILE MANAGEMENT
    // ---------------------------------------------------------
    public static boolean parseFiles(File rootFolder) {
        // resets lists before each load to avoid duplicates
        paises.clear();
        relatorio.clear();

        File folder = new File(rootFolder, "test-files");
        File paisesFile = new File(folder, "test-paises.csv");

        if (!paisesFile.exists()) {
            System.out.println("Erro: Ficheiro " + paisesFile.getPath() + " não encontrado.");
            return false;
        }

        return readPaises(paisesFile);
    }

    // -- —— specific reader for paises.csv —— --
    private static boolean readPaises(File file) {
        int validLines = 0;
        int invalidLines = 0;
        int firstInvalidLine = -1;
        int currentLine = 1;

        try {
            Scanner reader = new Scanner(file);

            // skip header row (line 1)
            if (reader.hasNextLine()) {
                reader.nextLine();
                currentLine++;
            }

            // process file line by line
            while (reader.hasNextLine()) {
                String row = reader.nextLine();
                String[] data = row.split(",");

                if (data.length == 4) {
                    // valid row
                    int id = Integer.parseInt(data[0]); // convert first column data into int
                    String alfa2 = data[1];
                    String alfa3 = data[2];
                    String nome = data[3];

                    // create object to add to memory
                    Pais p = new Pais(nome, id, alfa2, alfa3);
                    paises.add(p);
                    validLines++;

                } else {
                    // invalid row
                    invalidLines++;

                    if (firstInvalidLine == -1) { // only save line num of first error
                        firstInvalidLine = currentLine;
                    }
                }

                currentLine++;
            }

            reader.close();

            InputInvalido relatorioInfo = new InputInvalido("test-paises.csv", validLines, invalidLines, firstInvalidLine);
            relatorio.add(relatorioInfo);

            return true;

        } catch (Exception e) {
            // error opening or reading file
            return false;
        }
    }

    // ---------------------------------------------------------
    // USER INTERFACE
    // ---------------------------------------------------------
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("==========================================");
            System.out.println("            DEISI WORLD METER             ");
            System.out.println("==========================================");
            System.out.println("[1] Carregar Ficheiros");
            System.out.println("[2] Listar Países");
            System.out.println("[3] Listar Cidades");
            System.out.println("[4] Listar Populações");
            System.out.println("[5] Relatório de Erros");
            System.out.println("[6] Sair");
            System.out.println("------------------------------------------");
            System.out.print("Seleção > ");

            String option = input.nextLine();

            // -- —— option 1: load data —— --
            if (option.equals("1")) {
                long start = System.currentTimeMillis();
                boolean parseOk = parseFiles(new File("."));
                long end = System.currentTimeMillis();

                if (parseOk) {
                    System.out.println("Lido com sucesso em " + (end - start) + "ms");
                } else {
                    System.out.println("Erro ao carregar ficheiros.");
                }
            }

            // -- —— option 2: load país data —— --
            if (option.equals("2")) {
                for (Pais p : paises) {
                    System.out.println(p);
                }
            }

            // -- —— option 3: load cidade data —— --
            if (option.equals("3")) {
                break;
            }

            // -- —— option 4: load população data —— --
            if (option.equals("4")) {
                break;
            }

            // -- —— option 5: load relatório —— --
            if (option.equals("5")) {
                for (InputInvalido error : relatorio) {
                    System.out.println(error);
                }
            }

            // -- —— option 6: exit program —— --
            if (option.equals("6")) {
                break;
            }

        }

        input.close();
    }
}
