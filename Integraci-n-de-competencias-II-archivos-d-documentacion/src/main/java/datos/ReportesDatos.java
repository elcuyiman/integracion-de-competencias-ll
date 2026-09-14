package datos;

import apoyo.ReglasHorario;
import conexion.ConexionBaseDatos;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Consultas de los tres reportes que solicita el caso.
 *
 * Los umbrales de horario no se escriben aqui: se toman de {@link ReglasHorario},
 * de modo que un cambio de horario de la empresa se aplique en un solo punto del
 * sistema y quede cubierto por las pruebas unitarias.
 */
public class ReportesDatos {

    /**
     * RE-01: entradas posteriores al limite de entrada definido en las reglas.
     */
    public DefaultTableModel obtenerAtrasos() throws SQLException {
        String sql = """
                SELECT u.id AS usuario_id, u.nombre, u.correo, a.fecha, a.hora
                FROM asistencias a
                INNER JOIN usuarios u ON u.id = a.usuario_id
                WHERE a.tipo = 'ENTRADA' AND a.hora > ?
                ORDER BY a.fecha DESC, a.hora DESC
                """;
        return ejecutarReporte(sql, ReglasHorario.limiteEntradaSql(), "Fecha entrada", "Hora entrada");
    }

    /**
     * RE-02: salidas anteriores a la hora minima de salida definida en las reglas.
     */
    public DefaultTableModel obtenerSalidasAnticipadas() throws SQLException {
        String sql = """
                SELECT u.id AS usuario_id, u.nombre, u.correo, a.fecha, a.hora
                FROM asistencias a
                INNER JOIN usuarios u ON u.id = a.usuario_id
                WHERE a.tipo = 'SALIDA' AND a.hora < ?
                ORDER BY a.fecha DESC, a.hora DESC
                """;
        return ejecutarReporte(sql, ReglasHorario.minimaSalidaSql(), "Fecha salida", "Hora salida");
    }

    /**
     * RE-03: usuarios activos sin ninguna marca en la fecha indicada.
     */
    public DefaultTableModel obtenerInasistencias(LocalDate fecha) throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID usuario", "Nombre", "Correo", "Fecha sin marcas"},
                0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        String sql = """
                SELECT u.id AS usuario_id, u.nombre, u.correo
                FROM usuarios u
                WHERE u.rol = 'USUARIO'
                  AND u.activo = 1
                  AND NOT EXISTS (
                      SELECT 1
                      FROM asistencias a
                      WHERE a.usuario_id = u.id
                        AND a.fecha = ?
                  )
                ORDER BY u.nombre
                """;

        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setDate(1, Date.valueOf(fecha));

            try (ResultSet resultado = consulta.executeQuery()) {
                while (resultado.next()) {
                    modelo.addRow(new Object[]{
                            resultado.getInt("usuario_id"),
                            resultado.getString("nombre"),
                            resultado.getString("correo"),
                            Date.valueOf(fecha)
                    });
                }
            }
        }
        return modelo;
    }

    /**
     * Ejecuta un reporte de marcas cuyo unico parametro es el umbral de hora.
     */
    private DefaultTableModel ejecutarReporte(String sql, String umbralHora,
                                              String columnaFecha, String columnaHora) throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID usuario", "Nombre", "Correo", columnaFecha, columnaHora},
                0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setString(1, umbralHora);

            try (ResultSet resultado = consulta.executeQuery()) {
                while (resultado.next()) {
                    modelo.addRow(new Object[]{
                            resultado.getInt("usuario_id"),
                            resultado.getString("nombre"),
                            resultado.getString("correo"),
                            resultado.getDate("fecha"),
                            resultado.getTime("hora")
                    });
                }
            }
        }
        return modelo;
    }
}