package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;


public class TestSumPopulations {
    @Test
    public void sumPopulations() {
        assertTrue(Main.parseFiles(new File("test-files/sumPopulations")));

        // Caso 1: um país só
        Result r1 = Main.execute("SUM_POPULATIONS Wakanda");
        assertNotNull(r1);
        assertTrue(r1.success);
        assertEquals("500000", r1.result);

        // Caso 2: lista separada por vírgula
        Result r2 = Main.execute("SUM_POPULATIONS Wakanda,Asgard");
        assertTrue(r2.success);
        assertEquals("750000", r2.result);

        // Caso 3: país inválido (convenção success=true, mensagem em result)
        Result r3 = Main.execute("SUM_POPULATIONS Atlantis");
        assertTrue(r3.success);
        assertEquals("Pais invalido: Atlantis", r3.result);
    }
}
