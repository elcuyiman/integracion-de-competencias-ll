package datos;

import conexion.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class AsistenciasDatos {
    public void marcarAsistencia(int usuarioId, String tipo) throws SQLException {
        LocalDate fecha = LocalDate.now();
        if (existeMarca(usuarioId, tipo, fecha)) {
            throw new SQLException("Marca repetida", "MARCA_DUPLICADA");
        }

        guardarMarca(usuarioId, tipo, fecha, LocalTime.now());
    }

    private void guardarMarca(int usuarioId, String tipo, LocalDate fecha, LocalTime hora) throws SQLException {
        String sql = """
                INSERT INTO asistencias (usuario_id, tipo, fecha, hora)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, usuarioId);
            consulta.setString(2, tipo);
            consulta.setDate(3, Date.valueOf(fecha));
            consulta.setTime(4, Time.valueOf(hora));
            consulta.executeUpdate();
        }
    }

    private boolean existeMarca(int usuarioId, String tipo, LocalDate fecha) throws SQLException {
        String sql = """
                SELECT COUNT(*) AS total
                FROM asistencias
                WHERE usuario_id = ? AND tipo = ? AND fecha = ?
                """;

        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, usuarioId);
            consulta.setString(2, tipo);
            consulta.setDate(3, Date.valueOf(fecha));

            try (ResultSet resultado = consulta.executeQuery()) {
                return resultado.next() && resultado.getInt("total") > 0;
            }
        }
    }
}
