package pt.ulusofona.aed.deisiworldmeter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;



public class TestGetCitiesByCountry {
    @Test
    public void getCitiesByCountry() {
        assertTrue(Main.parseFiles(new File("test-files/getCitiesByCountry")));

        // Caso 1: pedir 2 de um país com 3 -> apanha as 2 primeiras (ordem do ficheiro)
        Result r1 = Main.execute("GET_CITIES_BY_COUNTRY 2 Wakanda");
        assertNotNull(r1);
        assertTrue(r1.success);
        assertArrayEquals(new String[] {
                "Birnin Zana 1",
                "Birnin Zana 2"
        }, r1.result.split("\n"));

        // Caso 2: pedir mais do que existem -> devolve só as que há
        Result r2 = Main.execute("GET_CITIES_BY_COUNTRY 3 Lalaland");
        assertTrue(r2.success);
        assertArrayEquals(new String[] { "Land of sunshine" }, r2.result.split("\n"));
    }
}
