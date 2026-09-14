package ventanas;

import apoyo.Mensajes;
import apoyo.PanelFondo;
import datos.AsistenciasDatos;
import entidades.Usuario;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class VentanaAsistencia extends JFrame {
    private final Usuario usuario;
    private final AsistenciasDatos asistenciasDatos = new AsistenciasDatos();
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final Timer reloj = new Timer(1000, event -> actualizarHora());

    public VentanaAsistencia() {
        this(new Usuario(0, "Usuario", "", "USUARIO"));
    }

    public VentanaAsistencia(Usuario usuario) {
        this.usuario = usuario;
        setContentPane(new PanelFondo("/imagenes/fondo_asistencia.jpg"));
        initComponents();
        setSize(395, 210);
        setLocationRelativeTo(null);
        etiquetaBienvenida.setText("Usuario: " + usuario.getNombre());
        actualizarHora();
        reloj.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaBienvenida = new javax.swing.JLabel();
        etiquetaHora = new javax.swing.JLabel();
        botonEntrada = new javax.swing.JButton();
        botonSalida = new javax.swing.JButton();
        botonCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Control de asistencia");
        setResizable(false);

        etiquetaBienvenida.setFont(etiquetaBienvenida.getFont().deriveFont(16f));
        etiquetaBienvenida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaBienvenida.setText("Usuario:");

        etiquetaHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaHora.setText("Hora actual: 00:00:00");

        botonEntrada.setText("Marcar entrada");
        botonEntrada.addActionListener(this::botonEntradaActionPerformed);

        botonSalida.setText("Marcar salida");
        botonSalida.addActionListener(this::botonSalidaActionPerformed);

        botonCerrar.setText("Cerrar sesion");
        botonCerrar.addActionListener(this::botonCerrarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetaBienvenida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(etiquetaHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonSalida, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(etiquetaBienvenida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiquetaHora)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonEntrada)
                    .addComponent(botonSalida))
                .addGap(18, 18, 18)
                .addComponent(botonCerrar)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEntradaActionPerformed
        marcar("ENTRADA");
    }//GEN-LAST:event_botonEntradaActionPerformed

    private void botonSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalidaActionPerformed
        marcar("SALIDA");
    }//GEN-LAST:event_botonSalidaActionPerformed

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_botonCerrarActionPerformed

    private void actualizarHora() {
        etiquetaHora.setText("Hora actual: " + LocalTime.now().format(formatoHora));
    }

    private void marcar(String tipo) {
        try {
            asistenciasDatos.marcarAsistencia(usuario.getId(), tipo);
            JOptionPane.showMessageDialog(this, "Se guardo la " + tipo.toLowerCase() + ".");
        } catch (SQLException e) {
            if ("MARCA_DUPLICADA".equals(e.getSQLState())) {
                JOptionPane.showMessageDialog(this, "Eso ya esta marcado por hoy.");
            } else {
                Mensajes.mostrarErrorConexion(this);
            }
        }
    }

    private void cerrarSesion() {
        new VentanaLogin().setVisible(true);
        dispose();
    }

    @Override
    public void dispose() {
        reloj.stop();
        super.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCerrar;
    private javax.swing.JButton botonEntrada;
    private javax.swing.JButton botonSalida;
    private javax.swing.JLabel etiquetaBienvenida;
    private javax.swing.JLabel etiquetaHora;
    // End of variables declaration//GEN-END:variables
}
