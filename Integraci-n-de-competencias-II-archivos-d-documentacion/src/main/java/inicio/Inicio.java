    package inicio;

import ventanas.VentanaLogin;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Inicio {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            VentanaLogin ventanaLogin = new VentanaLogin();
            ventanaLogin.setVisible(true);
        });
    }
}
