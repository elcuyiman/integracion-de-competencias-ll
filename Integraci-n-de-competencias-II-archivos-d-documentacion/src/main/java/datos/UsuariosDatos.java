package datos;

import apoyo.Claves;
import conexion.ConexionBaseDatos;
import entidades.Usuario;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDatos {
    public DefaultTableModel obtenerTablaUsuarios() throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Correo", "Rol", "Activo"},
                0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        String sql = """
                SELECT id, nombre, correo, rol, activo
                FROM usuarios
                ORDER BY activo DESC, rol, nombre
                """;

        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                modelo.addRow(new Object[]{
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("correo"),
                        resultado.getString("rol"),
                        resultado.getBoolean("activo") ? "SI" : "NO"
                });
            }
        }
        return modelo;
    }

    public List<Usuario> obtenerUsuariosActivos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = """
                SELECT id, nombre, correo, rol, activo
                FROM usuarios
                WHERE rol = 'USUARIO' AND activo = 1
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                usuarios.add(new Usuario(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("correo"),
                        resultado.getString("rol"),
                        resultado.getBoolean("activo")
                ));
            }
        }
        return usuarios;
    }

    public void crearUsuario(String nombre, String correo, String clave, String rol) throws SQLException {
        String sql = """
                INSERT INTO usuarios (nombre, correo, password_hash, rol, activo)
                VALUES (?, ?, ?, ?, 1)
                """;

        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setString(1, nombre);
            consulta.setString(2, correo);
            consulta.setString(3, Claves.codificarClave(clave));
            consulta.setString(4, rol);
            consulta.executeUpdate();
        }
    }

    public void modificarUsuario(int id, String nombre, String correo, String rol, String nuevaClave) throws SQLException {
        boolean cambiarClave = nuevaClave != null && !nuevaClave.isBlank();
        String sql = cambiarClave
                ? """
                  UPDATE usuarios
                  SET nombre = ?, correo = ?, rol = ?, password_hash = ?
                  WHERE id = ?
                  """
                : """
                  UPDATE usuarios
                  SET nombre = ?, correo = ?, rol = ?
                  WHERE id = ?
                  """;

        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setString(1, nombre);
            consulta.setString(2, correo);
            consulta.setString(3, rol);
            if (cambiarClave) {
                consulta.setString(4, Claves.codificarClave(nuevaClave));
                consulta.setInt(5, id);
            } else {
                consulta.setInt(4, id);
            }
            consulta.executeUpdate();
        }
    }

    public void eliminarUsuario(int id) throws SQLException {
        String sql = "UPDATE usuarios SET activo = 0 WHERE id = ?";
        try (Connection conexion = ConexionBaseDatos.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, id);
            consulta.executeUpdate();
        }
    }
}
