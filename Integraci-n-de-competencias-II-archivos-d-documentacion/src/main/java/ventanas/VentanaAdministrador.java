package ventanas;

import apoyo.Mensajes;
import apoyo.PanelFondo;
import datos.AsistenciasDatos;
import entidades.Usuario;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VentanaAdministrador extends JFrame {
    private final Usuario usuario;
    private final AsistenciasDatos asistenciasDatos = new AsistenciasDatos();

    public VentanaAdministrador() {
        this(new Usuario(0, "Administrador", "", "ADMIN"));
    }

    public VentanaAdministrador(Usuario usuario) {
        this.usuario = usuario;
        setContentPane(new PanelFondo("/imagenes/fondo_administrador.jpg"));
        initComponents();
        setSize(390, 170);
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonAsistencia = new javax.swing.JButton();
        botonSalida = new javax.swing.JButton();
        botonUsuarios = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Panel administrador");
        setResizable(false);

        botonAsistencia.setText("Registrar asistencia");
        botonAsistencia.addActionListener(this::botonAsistenciaActionPerformed);

        botonSalida.setText("Registrar salida");
        botonSalida.addActionListener(this::botonSalidaActionPerformed);

        botonUsuarios.setText("Gestionar usuarios");
        botonUsuarios.addActionListener(this::botonUsuariosActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonAsistencia, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                    .addComponent(botonSalida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(botonAsistencia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonSalida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonUsuarios)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAsistenciaActionPerformed
        marcar("ENTRADA");
    }//GEN-LAST:event_botonAsistenciaActionPerformed

    private void botonSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalidaActionPerformed
        marcar("SALIDA");
    }//GEN-LAST:event_botonSalidaActionPerformed

    private void botonUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonUsuariosActionPerformed
        new VentanaUsuarios(usuario).setVisible(true);
    }//GEN-LAST:event_botonUsuariosActionPerformed

    private void marcar(String tipo) {
        try {
            asistenciasDatos.marcarAsistencia(usuario.getId(), tipo);
            if ("SALIDA".equals(tipo)) {
                JOptionPane.showMessageDialog(this, "Salida guardada.");
            } else {
                JOptionPane.showMessageDialog(this, "Asistencia guardada.");
            }
        } catch (SQLException e) {
            if ("MARCA_DUPLICADA".equals(e.getSQLState())) {
                JOptionPane.showMessageDialog(this, "Eso ya esta marcado por hoy.");
            } else {
                Mensajes.mostrarErrorConexion(this);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAsistencia;
    private javax.swing.JButton botonSalida;
    private javax.swing.JButton botonUsuarios;
    // End of variables declaration//GEN-END:variables
}
