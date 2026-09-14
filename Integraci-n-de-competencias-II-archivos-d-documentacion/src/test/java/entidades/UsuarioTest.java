package entidades;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias de la entidad Usuario.
 */
class UsuarioTest {

    @Test
    @DisplayName("El constructor de cuatro argumentos deja al usuario activo")
    void constructorDeCuatroArgumentosDejaUsuarioActivo() {
        Usuario usuario = new Usuario(1, "Camila Rojas", "camila@empresa.cl", "USUARIO");

        assertTrue(usuario.isActivo());
    }

    @Test
    @DisplayName("Los getters devuelven los valores entregados al constructor")
    void gettersDevuelvenLosValoresDelConstructor() {
        Usuario usuario = new Usuario(3, "Diego Soto", "diego@empresa.cl", "USUARIO", true);

        assertEquals(3, usuario.getId());
        assertEquals("Diego Soto", usuario.getNombre());
        assertEquals("diego@empresa.cl", usuario.getCorreo());
        assertEquals("USUARIO", usuario.getRol());
        assertTrue(usuario.isActivo());
    }

    @Test
    @DisplayName("Un usuario eliminado logicamente queda inactivo")
    void usuarioEliminadoLogicamenteQuedaInactivo() {
        Usuario usuario = new Usuario(5, "Logistica", "log@empresa.cl", "USUARIO", false);

        assertFalse(usuario.isActivo());
    }

    @Test
    @DisplayName("esAdministrador reconoce el rol ADMIN sin importar mayusculas")
    void esAdministradorReconoceRolAdmin() {
        assertTrue(new Usuario(1, "Admin", "admin@empresa.cl", "ADMIN").esAdministrador());
        assertTrue(new Usuario(1, "Admin", "admin@empresa.cl", "admin").esAdministrador());
    }

    @Test
    @DisplayName("esAdministrador es falso para el rol USUARIO y para rol nulo")
    void esAdministradorEsFalsoParaUsuarioYNulo() {
        assertFalse(new Usuario(2, "Camila Rojas", "camila@empresa.cl", "USUARIO").esAdministrador());
        assertFalse(new Usuario(2, "Sin rol", "sinrol@empresa.cl", null).esAdministrador());
    }

    @Test
    @DisplayName("toString entrega el nombre y el correo del usuario")
    void toStringEntregaNombreYCorreo() {
        Usuario usuario = new Usuario(4, "Javiera Lagos", "javiera@empresa.cl", "USUARIO");

        assertEquals("Javiera Lagos - javiera@empresa.cl", usuario.toString());
    }
}
