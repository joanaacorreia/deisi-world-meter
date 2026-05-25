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
    public static boolean parseFiles(File folder) {
        paises.clear();
        cidades.clear();
        populacoes.clear();
        relatorio.clear();


        boolean okPaises = readPaises(new File(folder, "paises.csv"));
        if (!okPaises){
            return false;
        }

        boolean okCidades = readCidades(new File(folder, "cidades.csv"));
        if (!okCidades){
            return false;
        }

        // Fase 2 - Remover países sem cidades
        ArrayList<Pais> paisesFiltrados = new ArrayList<>();
        for (Pais p : paises){
            if (paisTemCidade(p.getAlfa2())) {
                paisesFiltrados.add(p);
            }
        }
        paises = paisesFiltrados;

        boolean okPopulacao = readPopulacoes(new File(folder, "populacao.csv"));

        return okPopulacao;
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
                if (row.trim().isEmpty()){
                    continue;
                }

                // O "-1" obriga a contar todas as colunas, mesmo vazias
                String[] data = row.split(",", -1);

                if (data.length == 4) {
                    try {
                        int id = Integer.parseInt(data[0].trim());
                        String alfa2 = data[1].trim();
                        String alfa3 = data[2].trim();
                        String nome = data[3].trim();

                        // Só é válido se o nome não estiver vazio
                        if (!nome.isEmpty() && !paisIdExists(id)) {
                            paises.add(new Pais(nome, id, alfa2, alfa3));
                            validLines++;
                        } else {

                            invalidLines++;
                            if (firstInvalidLine == -1){
                                firstInvalidLine = currentLine;
                            }
                        }
                    } catch (NumberFormatException e) {
                        invalidLines++;
                        if (firstInvalidLine == -1){
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

            InputInvalido relatorioInfo = new InputInvalido("paises.csv", validLines, invalidLines, firstInvalidLine);
            relatorio.add(relatorioInfo);

            return true;

        } catch (Exception e) {
            // error opening or reading file
            System.out.println("Erro a ler: " + file.getAbsolutePath()); // Debug
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
                        String alfa2 = data[0].trim();
                        String nome = data[1].trim();
                        String regiao = data[2].trim();

                        // A população é obrigatória! Se estiver vazia, lança NumberFormatException
                        int populacao = (int) Double.parseDouble(data[3].trim());

                        double latitude = Double.parseDouble(data[4].trim());
                        double longitude = Double.parseDouble(data[5].trim());

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

            InputInvalido relatorioInfo = new InputInvalido("cidades.csv", validLines, invalidLines, firstInvalidLine);
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

                            // Incrementar o contador no país correspondente
                            for (Pais p : paises) {
                                if (p.getId() == id) {
                                    p.incrementarDadosPopulacao();
                                    break;
                                }
                            }
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

            InputInvalido relatorioInfo = new InputInvalido("populacao.csv", validLines, invalidLines, firstInvalidLine);
            relatorio.add(relatorioInfo);

            return true;

        } catch (Exception e) {
            // error opening or reading file
            return false;
        }
    }

    // ---------------------------------------------------------
    // SEARCHING HELPER METHODS -
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
    // FASE 2 --------------------------------------------------
    // ---------------------------------------------------------


    // Função Helper - Para o parseFiles() ignorar paises que não têm cidades
    // Esta função vai entrar no parseFiles() para remover os países que não têm
    // cidades do ArrayList dos países
    public static boolean paisTemCidade(String alfa2){
        // A partir do alfa2 no ficheiro paises.csv percorre os paises à
        // procura de um alfa2 igual ou inexistente
        for (Cidade c : cidades){
            if (c.alfa2.equals(alfa2)){
                return true;
            }
        }
        return false;
    }

    public static Result execute(String comando){
        // ==================== QUIT ==========================
        if(comando.equals("QUIT")){
            return new Result(true,null,"!Closing the Program");
        }

        // ==================== HELP ==========================
        if(comando.equals("HELP")){
            String helpcommand = "======================================= \n" +
                                 "Available commands: \n" +
                                 "\"COUNT_CITIES\" <min_population> \n" +
                                 "\"GET_CITIES_BY_COUNTRY\" <num_results> <country_name> \n" +
                                 "\"HELP\" - Shows this help message \n" +
                                 "\"QUIT\" - Exits the program";
            return new Result(true,null,helpcommand);
        }

        // ==================== COUNT_CITIES ==========================
        // Comportamento do Comando : Conta cidades com população >= a <min_populacao>
        // 1. Dividir a string por partes divido por cada espaço " "
        // 2. Verificar se o comando foi bem escrito neste caso tem duas partes COUNT_CITIES e <min_population>
        // 3. Dentro de um try, catch passar pelas cidades e ver qual cidade a população é >= a <min_population> e a partir daí aumentar o count
        if(comando.startsWith("COUNT_CITIES")){
            String[] partes = comando.split(" ");

            if (partes.length != 2){
                return new Result(false, "Comando invalido",null);
            }

            try {
                int minPopulacao = Integer.parseInt(partes[1].trim());
                int count = 0;

                for (Cidade c : cidades){
                    if (c.populacao >= minPopulacao){
                        count++;
                    }
                }

                return new Result(true,null,"" + count + "");
            }catch (NumberFormatException e){
                return new Result(false, "Comando invalido", null);
            }
        }

        // ==================== GET_CITIES_BY_COUNTRY ==========================
        if (comando.startsWith("GET_CITIES_BY_COUNTRY")){
            String[] partes = comando.split(" ",3); // Limite 3 para se houver paises com espaço

            if (partes.length != 3){
                return new Result(false, "Comando invalido",null);
            }

            try {
                 int numResults = Integer.parseInt(partes[1].trim());
                 String nomePais = partes[2].trim();

                 // Verificar se o país existe se não exister retornar "Pais invalido + ---"
                boolean paisValido = false;
                String alfa2Pais = null;

                for (Pais p : paises){
                    if (p.nome.equalsIgnoreCase(nomePais)){
                        paisValido = true;
                        alfa2Pais = p.getAlfa2();
                        break;
                    }
                }

                if (!paisValido){
                    return new Result(false, "Pais invalido : " + nomePais, null);
                }

                // Encontrar a cidade para o país que o user deu
                ArrayList<Cidade> cidadesDoPais = new ArrayList<>();
                for (Cidade c : cidades){
                    if (c.alfa2.equals(alfa2Pais)){
                        cidadesDoPais.add(c);
                    }
                }

                // Construir resultado só com os nomes das cidades
                String resultado = "";
                int limite = Math.min(numResults, cidadesDoPais.size());
                for (int i = 0; i < limite; i++) {
                    resultado += cidadesDoPais.get(i).nome + "\n";
                }

                return new Result(true, null, resultado);

            }catch (NumberFormatException e){
                return new Result(false, "Comando invalido", null);
            }


        }

        // == Erro Final ==

        return new Result(false,"Unknown Command" + comando,null);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Deisi World Meter");

        long start = System.currentTimeMillis();
        boolean parseOk = parseFiles(new File("."));
        if (!parseOk){
            System.out.println("Error Loading Files");
            return;
        }
        long end = System.currentTimeMillis();

        System.out.println("Loaded files in " + (end - start) + "ms");

        Result result = execute("HELP");
        System.out.println(result.result);

        Scanner in = new Scanner(System.in);

        String line;
        do {
            System.out.println(">");
            line = in.nextLine();

            if (line != null && !line.equals("Quit")){
                start = System.currentTimeMillis();
                result = execute(line);
                end = System.currentTimeMillis();

                if(!result.success){
                    System.out.println("Error: " + result.error);
                } else {
                    System.out.println(result.result);
                    System.out.println("(took " + (end - start) + " ms)");
                }
            }
        } while (line != null && !line.equals("QUIT"));
    }

    // ---------------------------------------------------------
    // USER INTERFACE
    // ---------------------------------------------------------
    /*
        public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("==========================================");
            System.out.println("            DEISI WORLD METER             ");
            System.out.println("==========================================");
            System.out.println("[1] Carregar Ficheiros");
            System.out.println("[2] Listar Pa&iacute;ses");
            System.out.println("[3] Listar Cidades");
            System.out.println("[4] Listar Popula&ccedil;&otilde;es");
            System.out.println("[5] Relat&oacute;rio de Erros");
            System.out.println("[6] Sair");
            System.out.println("------------------------------------------");
            System.out.print("Sele&ccedil;&atilde;o > ");

            String option = input.nextLine();

            // -- &mdash;&mdash; option 1: load data &mdash;&mdash; --
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

            // -- &mdash;&mdash; option 2: load pa&iacute;s data &mdash;&mdash; --
            if (option.equals("2")) {
                System.out.println();
                System.out.println(String.format("%-20s | %-5s | %-5s | %-5s",
                        "Nome", "ID", "Alfa2", "Alfa3"));
                System.out.println("&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;");
                for (Pais p : paises) {
                    System.out.println(p);
                }
                System.out.println();
            }

            // -- &mdash;&mdash; option 3: load cidade data &mdash;&mdash; --
            if (option.equals("3")) {
                System.out.println();
                System.out.println(String.format("%-5s | %-20s | %-10s | %-10s | %-20s",
                        "Alfa2", "Cidade", "Regiao", "Popula&ccedil;&atilde;o", "Coordenadas"));
                System.out.println("&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;");
                for (Cidade c : cidades) {
                    System.out.println(c);
                }
                System.out.println();
            }

            // -- &mdash;&mdash; option 4: load popula&ccedil;&atilde;o data &mdash;&mdash; --
            if (option.equals("4")) {
                System.out.println();
                System.out.println(String.format("%-5s | %-10s | %-20s | %-20s | %-10s",
                        "ID", "Ano", "Pop. Masculina", "Pop. Feminina", "Densidade"));
                System.out.println("&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;");
                for (Populacao p : populacoes) {
                    System.out.println(p);
                }
                System.out.println();
            }

            // -- &mdash;&mdash; option 5: load relat&oacute;rio &mdash;&mdash; --
            if (option.equals("5")) {
                System.out.println();
                System.out.println(String.format("%-20s | %-10s | %-10s | %-10s",
                        "Ficheiro", "V&aacute;lidas", "Inv&aacute;lidas", "1&ordf; Linha Inv&aacute;lida"));
                System.out.println("&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;");
                for (InputInvalido error : relatorio) {
                    System.out.println(error);
                }
                System.out.println();
            }

            // -- &mdash;&mdash; option 6: exit program &mdash;&mdash; --
            if (option.equals("6")) {
                break;
            }
        }

        input.close();
    }

     */
}


