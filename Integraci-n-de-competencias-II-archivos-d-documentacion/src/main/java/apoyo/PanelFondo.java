package apoyo;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {
    private final Image imagen;

    public PanelFondo(String ruta) {
        this.imagen = cargarImagen(ruta);
    }

    private Image cargarImagen(String ruta) {
        try {
            java.net.URL recurso = getClass().getResource(ruta);
            return recurso == null ? null : ImageIO.read(recurso);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
