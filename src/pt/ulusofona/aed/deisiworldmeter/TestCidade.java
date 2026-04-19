package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCidade {

    // Cenário 3: toString de Cidade
    @Test
    public void testToStringCidade_1() {
        Cidade c = new Cidade("PT", "Lisbon", "14", 517798, 38.716667, -9.133333);
        String esperado = "Lisbon | PT | 14 | 517798 | (38.716667,-9.133333)";
        assertEquals(esperado, c.toString(), "O toString da Cidade não está correto.");
    }

}