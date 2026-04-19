package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMain {

    // CENÁRIO 4: Leitura de 3 ficheiros sem erros

    @Test
    public void testParseFilesSemErros_1() {
        File pasta = new File("test-files/cenario4_1");
        boolean sucesso = Main.parseFiles(pasta);

        assertTrue(sucesso, "O método parseFiles deve retornar true.");

        ArrayList<?> paises = Main.getObjects(TipoEntidade.PAIS);
        assertEquals(2, paises.size(), "Deviam ter sido lidos 2 países.");

        ArrayList<?> cidades = Main.getObjects(TipoEntidade.CIDADE);
        assertEquals(4, cidades.size(), "Deviam ter sido lidas 4 cidades.");
    }

    @Test
    public void testParseFilesSemErros_2() {
        File pasta = new File("test-files/cenario4_2");
        boolean sucesso = Main.parseFiles(pasta);

        assertTrue(sucesso, "O método parseFiles deve retornar true.");

        ArrayList<?> paises = Main.getObjects(TipoEntidade.PAIS);
        assertEquals(3, paises.size(), "Deviam ter sido lidos 3 países.");

        ArrayList<?> cidades = Main.getObjects(TipoEntidade.CIDADE);
        assertEquals(6, cidades.size(), "Deviam ter sido lidas 6 cidades.");
    }

    // CENÁRIO 5: Leitura de ficheiros com erros

    @Test
    public void testParseFilesComErros_1() {
        File pasta = new File("test-files/cenario5_1");
        Main.parseFiles(pasta);

        ArrayList<InputInvalido> erros = (ArrayList<InputInvalido>) Main.getObjects(TipoEntidade.INPUT_INVALIDO);

        assertEquals(3, erros.size(), "O relatório tem de ter 3 entradas.");

        assertEquals("paises.csv | 1 | 2 | 3", erros.get(0).toString(), "Relatório de paises.csv falhou.");
        assertEquals("cidades.csv | 1 | 2 | 3", erros.get(1).toString(), "Relatório de cidades.csv falhou.");
        assertEquals("populacao.csv | 1 | 2 | 3", erros.get(2).toString(), "Relatório de populacao.csv falhou.");
    }

    @Test
    public void testParseFilesComErros_2() {
        File pasta = new File("test-files/cenario5_2");
        Main.parseFiles(pasta);

        ArrayList<InputInvalido> erros = (ArrayList<InputInvalido>) Main.getObjects(TipoEntidade.INPUT_INVALIDO);

        assertEquals(3, erros.size(), "O relatório tem de ter 3 entradas.");

        assertEquals("paises.csv | 2 | 1 | 4", erros.get(0).toString(), "Relatório de paises.csv falhou.");
        assertEquals("cidades.csv | 2 | 1 | 4", erros.get(1).toString(), "Relatório de cidades.csv falhou.");
        assertEquals("populacao.csv | 2 | 1 | 4", erros.get(2).toString(), "Relatório de populacao.csv falhou.");
    }
}