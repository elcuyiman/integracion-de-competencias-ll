package ventanas;

import apoyo.Mensajes;
import apoyo.PanelFondo;
import datos.AccesoDatos;
import entidades.Usuario;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class VentanaLogin extends JFrame {
    private final AccesoDatos accesoDatos = new AccesoDatos();
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final Timer reloj = new Timer(1000, event -> actualizarHora());

    public VentanaLogin() {
        setContentPane(new PanelFondo("/imagenes/fondo_login.jpg"));
        initComponents();
        setSize(390, 235);
        setLocationRelativeTo(null);
        actualizarHora();
        reloj.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaTitulo = new javax.swing.JLabel();
        etiquetaHora = new javax.swing.JLabel();
        etiquetaCorreo = new javax.swing.JLabel();
        campoCorreo = new javax.swing.JTextField();
        etiquetaClave = new javax.swing.JLabel();
        campoClave = new javax.swing.JPasswordField();
        botonIngresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Asistencia - Login");
        setResizable(false);

        etiquetaTitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        etiquetaTitulo.setForeground(new java.awt.Color(255, 255, 255));
        etiquetaTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaTitulo.setText("Control de asistencia");

        etiquetaHora.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        etiquetaHora.setForeground(new java.awt.Color(255, 255, 255));
        etiquetaHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaHora.setText("Hora actual: 00:00:00");

        etiquetaCorreo.setForeground(new java.awt.Color(255, 255, 255));
        etiquetaCorreo.setText("Correo:");

        campoCorreo.setColumns(22);

        etiquetaClave.setForeground(new java.awt.Color(255, 255, 255));
        etiquetaClave.setText("Contrasena:");

        campoClave.setColumns(22);
        campoClave.addActionListener(this::campoClaveActionPerformed);

        botonIngresar.setText("Ingresar");
        botonIngresar.addActionListener(this::botonIngresarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetaTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(etiquetaHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonIngresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etiquetaCorreo)
                            .addComponent(etiquetaClave))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoCorreo)
                            .addComponent(campoClave))))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(etiquetaTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiquetaHora)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaCorreo)
                    .addComponent(campoCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaClave)
                    .addComponent(campoClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(botonIngresar)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIngresarActionPerformed
        iniciarSesion();
    }//GEN-LAST:event_botonIngresarActionPerformed

    private void campoClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoClaveActionPerformed
        iniciarSesion();
    }//GEN-LAST:event_campoClaveActionPerformed

    private void actualizarHora() {
        etiquetaHora.setText("Hora actual: " + LocalTime.now().format(formatoHora));
    }

    private void iniciarSesion() {
        String correo = campoCorreo.getText().trim();
        String clave = new String(campoClave.getPassword());

        if (correo.isBlank() || clave.isBlank()) {
            JOptionPane.showMessageDialog(this, "Falta poner el correo o la contrasena.");
            return;
        }

        try {
            Usuario usuario = accesoDatos.iniciarSesion(correo, clave);
            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "El correo o clave no esta bien.");
                return;
            }

            JFrame siguiente = usuario.esAdministrador() ? new VentanaAdministrador(usuario) : new VentanaAsistencia(usuario);
            siguiente.setVisible(true);
            dispose();
            } catch (SQLException e) { 
            Mensajes.mostrarErrorConexion(this); }
            }

    @Override
    public void dispose() {
        reloj.stop();
        super.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonIngresar;
    private javax.swing.JPasswordField campoClave;
    private javax.swing.JTextField campoCorreo;
    private javax.swing.JLabel etiquetaClave;
    private javax.swing.JLabel etiquetaCorreo;
    private javax.swing.JLabel etiquetaHora;
    private javax.swing.JLabel etiquetaTitulo;
    // End of variables declaration//GEN-END:variables
}
