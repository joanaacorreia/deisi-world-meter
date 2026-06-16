package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;



public class TestCountCities {
    @Test
    public void countCities() {
        assertTrue(Main.parseFiles(new File("test-files/countCities")));

        // Caso 1: threshold baixo -> todas as cidades
        Result r1 = Main.execute("COUNT_CITIES 100000");
        assertNotNull(r1);
        assertTrue(r1.success);
        assertEquals("5", r1.result);

        // Caso 2: threshold intermédio -> só algumas
        Result r2 = Main.execute("COUNT_CITIES 500000");
        assertTrue(r2.success);
        assertEquals("3", r2.result);

        // Caso 3: threshold alto -> nenhuma
        Result r3 = Main.execute("COUNT_CITIES 1000000");
        assertTrue(r3.success);
        assertEquals("0", r3.result);
    }
}
