package apoyo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias de la codificacion de contrasenas.
 */
class ClavesTest {

    @Test
    @DisplayName("El hash tiene 64 caracteres hexadecimales en minuscula")
    void hashTiene64CaracteresHexadecimales() {
        String hash = Claves.codificarClave("usuario123");

        assertEquals(64, hash.length());
        assertTrue(hash.matches("[0-9a-f]{64}"));
    }

    @Test
    @DisplayName("La misma clave produce siempre el mismo hash")
    void mismaClaveProduceMismoHash() {
        assertEquals(Claves.codificarClave("clave1"), Claves.codificarClave("clave1"));
    }

    @Test
    @DisplayName("Claves distintas producen hashes distintos")
    void clavesDistintasProducenHashesDistintos() {
        assertNotEquals(Claves.codificarClave("clave1"), Claves.codificarClave("clave2"));
    }

    @Test
    @DisplayName("El hash coincide con el que genera SHA2() de MySQL en schema.sql")
    void hashCoincideConElGeneradoPorMySql() {
        // Valores obtenidos con SELECT SHA2('admin123', 256) en MySQL.
        // Si esta prueba falla, el login no encontrara al usuario sembrado por el script.
        assertEquals(
                "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9",
                Claves.codificarClave("admin123"));

        assertEquals(
                "dfa7a2273567dcd1efffb9a46308e91c20fa13c44c3441bc69cd6a7869b3f7fd",
                Claves.codificarClave("usuario123"));
    }

    @Test
    @DisplayName("La codificacion distingue mayusculas de minusculas")
    void codificacionDistingueMayusculas() {
        assertNotEquals(Claves.codificarClave("admin123"), Claves.codificarClave("ADMIN123"));
    }
}
