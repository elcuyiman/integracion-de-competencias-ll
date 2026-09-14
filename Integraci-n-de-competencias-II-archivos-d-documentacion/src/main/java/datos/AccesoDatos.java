package datos;

import apoyo.Claves;
import conexion.ConexionBaseDatos;
import entidades.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesoDatos {
    public Usuario iniciarSesion(String correo, String clave) throws SQLException {
        String sql = """
                SELECT id, nombre, correo, rol, activo
                FROM usuarios
                WHERE correo = ? AND password_hash = ? AND activo = 1
                """;

        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setString(1, correo);
            consulta.setString(2, Claves.codificarClave(clave));

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    return new Usuario(
                            resultado.getInt("id"),
                            resultado.getString("nombre"),
                            resultado.getString("correo"),
                            resultado.getString("rol"),
                            resultado.getBoolean("activo")
                    );
                }
            }
        }
        return null;
    }
}
