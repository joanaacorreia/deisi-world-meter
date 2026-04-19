package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPais {

    // Cenário 1: toString de País com ID < 700
    @Test
    public void testToStringPaisIdMenor700_1() {
        Pais p = new Pais("Portugal", 620, "PT", "PRT");
        String esperado = "Portugal | 620 | PT | PRT";
        assertEquals(esperado, p.toString(), "O toString do País (ID < 700) não está correto.");
    }
    // Podemos criar mais 4 testes deste estilo só mudar o país e etc... para ter a certeza que está correto




}