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
        cidades.clear();
        populacoes.clear();
        relatorio.clear();

        File folder = new File(rootFolder, "test-files");

        // read paises
        boolean paisesSuccessful = readPaises(new File(folder, "test-paises.csv"));
        if (!paisesSuccessful) {
            return false;
        }

        // read cidades
        boolean cidadesSuccessful = readCidades(new File(folder, "test-cidades.csv"));
        if (!cidadesSuccessful) {
            return false;
        }

        // read populações
        return readPopulacoes(new File(folder, "test-populacao.csv"));
    }

    // ---------------------------------------------------------
    // FILE READERS
    // ---------------------------------------------------------
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
                    int id = Integer.parseInt(data[0]);
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

    // -- —— specific reader for cidades.csv —— --
    private static boolean readCidades(File file) {
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
                String[] data = row.split(",", -1); // -1 keeps empty places from being ignored

                if (data.length == 6 && paisExists(data[0])) {
                    try {
                        // valid row
                        String alfa2 = data[0];
                        String nome = data[1];
                        String regiao = data[2];

                        // handle empty population
                        int populacao = 0;
                        if (!data[3].isEmpty()) {
                            // first parse as double, then int
                            populacao = (int) Double.parseDouble(data[3]);
                        }

                        double latitude = Double.parseDouble(data[4]);
                        double longitude = Double.parseDouble(data[5]);

                        // create object to add to memory
                        Cidade c = new Cidade(alfa2, nome, regiao, populacao, latitude, longitude);
                        cidades.add(c);
                        validLines++;

                    } catch (NumberFormatException e) {
                        // catches lines where numbers are malformed
                        invalidLines++;

                        if (firstInvalidLine == -1) { // only save line num of first error
                            firstInvalidLine = currentLine;
                        }
                    }
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

            InputInvalido relatorioInfo = new InputInvalido("test-cidades.csv", validLines, invalidLines, firstInvalidLine);
            relatorio.add(relatorioInfo);

            return true;

        } catch (Exception e) {
            // error opening or reading file
            return false;
        }
    }

    // -- —— specific reader for populacao.csv —— --
    private static boolean readPopulacoes(File file) {
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

            while (reader.hasNextLine()) {
                String row = reader.nextLine();
                String[] data = row.split(",");

                if (data.length == 5) {
                    try {
                        int id = Integer.parseInt(data[0].trim());
                        String ano = data[1].trim();
                        long masculina = Long.parseLong(data[2].trim());
                        long feminina = Long.parseLong(data[3].trim());
                        double densidade = Double.parseDouble((data[4].trim()));

                        // validates if país id exists first
                        if (paisIdExists(id)) {
                            populacoes.add(new Populacao(id, ano, masculina, feminina, densidade));
                            validLines++;
                        } else {
                            invalidLines++;
                            if (firstInvalidLine == -1) {
                                firstInvalidLine = currentLine;
                            }
                        }
                    } catch (NumberFormatException e) {
                        invalidLines++;
                        if (firstInvalidLine == -1) {
                            firstInvalidLine = currentLine;
                        }
                    }
                } else {
                    invalidLines++;
                    if (firstInvalidLine == -1) {
                        firstInvalidLine = currentLine;
                    }
                }
                currentLine++;
            }
            reader.close();

            InputInvalido relatorioInfo = new InputInvalido("test-cidades.csv", validLines, invalidLines, firstInvalidLine);
            relatorio.add(relatorioInfo);

            return true;

        } catch (Exception e) {
            // error opening or reading file
            return false;
        }
    }

    // ---------------------------------------------------------
    // SEARCHING HELPER METHODS
    // ---------------------------------------------------------
    // -- —— check if country alfa2 code exists —— --
    public static boolean paisExists(String alfa2) {
        for (Pais p : paises) {
            if (p.getAlfa2().equals(alfa2)) {
                return true;
            }
        }
        return false;
    }

    // -- —— check if country id code exists —— --
    public static boolean paisIdExists(int id) {
        for (Pais p : paises) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
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
                boolean parseSuccessful = parseFiles(new File("."));
                long end = System.currentTimeMillis();

                if (parseSuccessful) {
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
                for (Cidade c : cidades) {
                    System.out.println(c);
                }
            }

            // -- —— option 4: load população data —— --
            if (option.equals("4")) {
                for (Populacao p : populacoes) {
                    System.out.println(p);
                }
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
