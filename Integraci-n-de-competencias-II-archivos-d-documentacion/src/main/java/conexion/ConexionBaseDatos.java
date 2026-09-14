package conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBaseDatos {
    private static final String ARCHIVO_CONFIGURACION = "config/db.properties";
    private static final String URL_DEFECTO = "jdbc:mysql://localhost:3306/asistencia_mvp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Santiago";
    private static final String USUARIO_DEFECTO = "asistencia_app";
    private static final String CLAVE_DEFECTO = "asistencia123";

    private ConexionBaseDatos() {
    }

    public static Connection obtenerConexion() throws SQLException {
        Properties propiedades = cargarPropiedades();

        String url = propiedades.getProperty("db.url", URL_DEFECTO);
        String usuario = propiedades.getProperty("db.user", USUARIO_DEFECTO);
        String clave = propiedades.getProperty("db.password", CLAVE_DEFECTO);

        return DriverManager.getConnection(url, usuario, clave);
    }

    private static Properties cargarPropiedades() {
        Properties propiedades = new Properties();
        try (FileInputStream entrada = new FileInputStream(ARCHIVO_CONFIGURACION)) {
            propiedades.load(entrada);
        } catch (IOException ignored) {
        }
        return propiedades;
    }
}
