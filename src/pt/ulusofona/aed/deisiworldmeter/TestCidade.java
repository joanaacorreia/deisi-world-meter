package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCidade {

    // CENÁRIO 3: Conversão para String de objetos Cidade

    @Test
    public void testToStringCidade_1_Normal() {
        Cidade c = new Cidade("PT", "Lisboa", "14", 517798, 38.716667, -9.133333);
        String esperado = "Lisboa | PT | 14 | 517798 | (38.716667,-9.133333)";
        assertEquals(esperado, c.toString(), "O toString da Cidade (Lisboa) falhou.");
    }

    @Test
    public void testToStringCidade_2_CoordenadasNegativas() {
        Cidade c = new Cidade("BR", "Brasilia", "DF", 2815660, -15.793889, -47.882778);
        String esperado = "Brasilia | BR | DF | 2815660 | (-15.793889,-47.882778)";
        assertEquals(esperado, c.toString(), "O toString com coordenadas negativas falhou.");
    }

    @Test
    public void testToStringCidade_3_PopulacaoZero() {
        Cidade c = new Cidade("AQ", "McMurdo Station", "00", 0, -77.846323, 166.668235);
        String esperado = "McMurdo Station | AQ | 00 | 0 | (-77.846323,166.668235)";
        assertEquals(esperado, c.toString(), "O toString com população a zero falhou.");
    }

    @Test
    public void testToStringCidade_4_CoordenadasPositivas() {
        Cidade c = new Cidade("JP", "Tokyo", "40", 8336599, 35.689444, 139.691667);
        String esperado = "Tokyo | JP | 40 | 8336599 | (35.689444,139.691667)";
        assertEquals(esperado, c.toString());
    }

    @Test
    public void testToStringCidade_5_NomeComposto() {
        Cidade c = new Cidade("US", "New York", "NY", 8175133, 40.712778, -74.005972);
        String esperado = "New York | US | NY | 8175133 | (40.712778,-74.005972)";
        assertEquals(esperado, c.toString());
    }
}