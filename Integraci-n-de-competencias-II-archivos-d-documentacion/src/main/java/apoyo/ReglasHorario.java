package apoyo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Reglas de negocio de horario definidas en el caso.
 *
 * Centraliza los umbrales que clasifican una marca como atraso o como salida
 * anticipada. Si la empresa cambia su horario, se modifica solo esta clase y
 * todos los reportes quedan consistentes.
 */
public final class ReglasHorario {

    /** Una entrada posterior a esta hora se considera atraso (RE-01). */
    public static final LocalTime HORA_LIMITE_ENTRADA = LocalTime.of(9, 30);

    /** Una salida anterior a esta hora se considera anticipada (RE-02). */
    public static final LocalTime HORA_MINIMA_SALIDA = LocalTime.of(17, 30);

    private static final DateTimeFormatter FORMATO_SQL = DateTimeFormatter.ofPattern("HH:mm:ss");

    private ReglasHorario() {
    }

    /**
     * Indica si una hora de entrada constituye atraso.
     * El limite es exclusivo: marcar exactamente a las 09:30 no es atraso.
     */
    public static boolean esAtraso(LocalTime horaEntrada) {
        return horaEntrada != null && horaEntrada.isAfter(HORA_LIMITE_ENTRADA);
    }

    /**
     * Indica si una hora de salida constituye salida anticipada.
     * El limite es exclusivo: marcar exactamente a las 17:30 no es anticipada.
     */
    public static boolean esSalidaAnticipada(LocalTime horaSalida) {
        return horaSalida != null && horaSalida.isBefore(HORA_MINIMA_SALIDA);
    }

    /** Umbral de entrada en el formato que espera MySQL (HH:mm:ss). */
    public static String limiteEntradaSql() {
        return HORA_LIMITE_ENTRADA.format(FORMATO_SQL);
    }

    /** Umbral de salida en el formato que espera MySQL (HH:mm:ss). */
    public static String minimaSalidaSql() {
        return HORA_MINIMA_SALIDA.format(FORMATO_SQL);
    }
}