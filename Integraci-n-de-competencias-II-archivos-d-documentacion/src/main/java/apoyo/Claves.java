package apoyo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Claves {
    private Claves() {
    }

    public static String codificarClave(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder resultado = new StringBuilder();
            for (byte b : hash) {
                resultado.append(String.format("%02x", b));
            }
            return resultado.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No se pudo preparar la clave", e);
        }
    }
}
