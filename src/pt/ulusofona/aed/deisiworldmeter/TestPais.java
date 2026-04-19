package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPais {


    // CENÁRIO 1: ID < 700

    @Test
    public void testToStringPaisIdMenor700_1() {
        Pais p = new Pais("Portugal", 620, "PT", "PRT");
        String esperado = "Portugal | 620 | PT | PRT";
        assertEquals(esperado, p.toString(), "O toString (ID < 700) deve ter 4 campos.");
    }

    @Test
    public void testToStringPaisIdMenor700_2() {
        Pais p = new Pais("Angola", 24, "AO", "AGO");
        String esperado = "Angola | 24 | AO | AGO";
        assertEquals(esperado, p.toString());
    }

    @Test
    public void testToStringPaisIdMenor700_3() {
        Pais p = new Pais("Brasil", 76, "BR", "BRA");
        String esperado = "Brasil | 76 | BR | BRA";
        assertEquals(esperado, p.toString());
    }

    @Test
    public void testToStringPaisIdMenor700_4() {
        Pais p = new Pais("Cabo Verde", 132, "CV", "CPV");
        String esperado = "Cabo Verde | 132 | CV | CPV";
        assertEquals(esperado, p.toString());
    }

    @Test
    public void testToStringPaisIdMenor700_5() {
        Pais p = new Pais("Franca", 250, "FR", "FRA");
        String esperado = "Franca | 250 | FR | FRA";
        assertEquals(esperado, p.toString());
    }




    // CENÁRIO 2: ID > 700

    @Test
    public void testToStringPaisIdMaior700_1() {
        Pais p = new Pais("Zambia", 894, "ZM", "ZMB");

        p.incrementarDadosPopulacao();
        p.incrementarDadosPopulacao();

        String esperado = "Zambia | 894 | ZM | ZMB | 2";
        assertEquals(esperado, p.toString(), "O toString (ID > 700) deve incluir o número de registos de população.");
    }

    @Test
    public void testToStringPaisIdMaior700_2() {
        Pais p = new Pais("Zimbabwe", 716, "ZW", "ZWE");

        for(int i = 0; i < 5; i++) {
            p.incrementarDadosPopulacao();
        }

        String esperado = "Zimbabwe | 716 | ZW | ZWE | 5";
        assertEquals(esperado, p.toString());
    }

    @Test
    public void testToStringPaisIdMaior700_3() {
        Pais p = new Pais("Venezuela", 862, "VE", "VEN");

        String esperado = "Venezuela | 862 | VE | VEN | 0";
        assertEquals(esperado, p.toString());
    }

    @Test
    public void testToStringPaisIdMaior700_4() {
        Pais p = new Pais("Vietname", 704, "VN", "VNM");

        for(int i = 0; i < 3; i++) {
            p.incrementarDadosPopulacao();
        }

        String esperado = "Vietname | 704 | VN | VNM | 3";
        assertEquals(esperado, p.toString());
    }

    @Test
    public void testToStringPaisIdMaior700_5() {
        Pais p = new Pais("Iemen", 887, "YE", "YEM");
        p.incrementarDadosPopulacao();

        String esperado = "Iemen | 887 | YE | YEM | 1";
        assertEquals(esperado, p.toString());
    }
}