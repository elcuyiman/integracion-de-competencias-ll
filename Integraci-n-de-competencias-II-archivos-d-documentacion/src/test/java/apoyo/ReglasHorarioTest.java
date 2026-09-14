package apoyo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias de las reglas de horario del caso (RE-01 y RE-02).
 */
class ReglasHorarioTest {

    @Test
    @DisplayName("RE-01: una entrada posterior a las 09:30 es atraso")
    void entradaPosteriorEsAtraso() {
        assertTrue(ReglasHorario.esAtraso(LocalTime.of(9, 45)));
        assertTrue(ReglasHorario.esAtraso(LocalTime.of(10, 5)));
    }

    @Test
    @DisplayName("RE-01: marcar exactamente a las 09:30 no es atraso")
    void entradaExactaNoEsAtraso() {
        assertFalse(ReglasHorario.esAtraso(LocalTime.of(9, 30)));
    }

    @Test
    @DisplayName("RE-01: una entrada anterior a las 09:30 no es atraso")
    void entradaAnteriorNoEsAtraso() {
        assertFalse(ReglasHorario.esAtraso(LocalTime.of(9, 10)));
    }

    @Test
    @DisplayName("RE-02: una salida anterior a las 17:30 es anticipada")
    void salidaAnteriorEsAnticipada() {
        assertTrue(ReglasHorario.esSalidaAnticipada(LocalTime.of(17, 20)));
        assertTrue(ReglasHorario.esSalidaAnticipada(LocalTime.of(16, 55)));
    }

    @Test
    @DisplayName("RE-02: marcar exactamente a las 17:30 no es salida anticipada")
    void salidaExactaNoEsAnticipada() {
        assertFalse(ReglasHorario.esSalidaAnticipada(LocalTime.of(17, 30)));
    }

    @Test
    @DisplayName("RE-02: una salida posterior a las 17:30 no es anticipada")
    void salidaPosteriorNoEsAnticipada() {
        assertFalse(ReglasHorario.esSalidaAnticipada(LocalTime.of(17, 45)));
    }

    @Test
    @DisplayName("Una hora nula no se clasifica en ninguna de las dos reglas")
    void horaNulaNoSeClasifica() {
        assertFalse(ReglasHorario.esAtraso(null));
        assertFalse(ReglasHorario.esSalidaAnticipada(null));
    }

    @Test
    @DisplayName("Los umbrales se formatean como los espera MySQL")
    void umbralesSeFormateanParaSql() {
        assertEquals("09:30:00", ReglasHorario.limiteEntradaSql());
        assertEquals("17:30:00", ReglasHorario.minimaSalidaSql());
    }
}
