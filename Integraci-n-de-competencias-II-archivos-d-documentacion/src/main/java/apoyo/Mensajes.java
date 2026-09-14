package apoyo;

import javax.swing.JOptionPane;
import java.awt.Component;

public class Mensajes {
    private Mensajes() {
    }

    public static void mostrarErrorConexion(Component parent) {
        JOptionPane.showMessageDialog(
                parent,
                "No se pudo hacer esto, revise conexion y pruebe otra vez.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static void mostrarErrorGeneral(Component parent) {
        JOptionPane.showMessageDialog(
                parent,
                "No se pudo hacer eso, intente otra vez.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
