package ventanas;

import apoyo.Mensajes;
import apoyo.PanelFondo;
import datos.ReportesDatos;
import datos.UsuariosDatos;
import entidades.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VentanaUsuarios extends JFrame {
    private final Usuario administradorActual;
    private final UsuariosDatos usuariosDatos = new UsuariosDatos();
    private final ReportesDatos reportesDatos = new ReportesDatos();

    public VentanaUsuarios() {
        this(new Usuario(0, "Administrador", "", "ADMIN"), false);
    }

    public VentanaUsuarios(Usuario administradorActual) {
        this(administradorActual, true);
    }

    private VentanaUsuarios(Usuario administradorActual, boolean cargarDatos) {
        this.administradorActual = administradorActual;
        setContentPane(new PanelFondo("/imagenes/fondo_usuarios.jpg"));
        initComponents();
        setSize(840, 620);
        setLocationRelativeTo(null);
        tablaUsuarios.setAutoCreateRowSorter(true);
        tablaReporte.setAutoCreateRowSorter(true);
        if (cargarDatos) {
            cargarUsuarios();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tituloVentana = new javax.swing.JLabel();
        panelTabla = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        botonCrear = new javax.swing.JButton();
        botonModificar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();
        botonActualizar = new javax.swing.JButton();
        botonCerrar = new javax.swing.JButton();
        tituloControl = new javax.swing.JLabel();
        panelTablaReporte = new javax.swing.JScrollPane();
        tablaReporte = new javax.swing.JTable();
        botonAtrasos = new javax.swing.JButton();
        botonSalidas = new javax.swing.JButton();
        botonInasistencias = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administracion");

        tituloVentana.setFont(tituloVentana.getFont().deriveFont(16f));
        tituloVentana.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloVentana.setText("Gestion de usuarios");

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Correo", "Rol", "Activo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        panelTabla.setViewportView(tablaUsuarios);

        botonCrear.setText("Crear usuario");
        botonCrear.addActionListener(this::botonCrearActionPerformed);

        botonModificar.setText("Modificar usuario");
        botonModificar.addActionListener(this::botonModificarActionPerformed);

        botonEliminar.setText("Eliminar usuario");
        botonEliminar.addActionListener(this::botonEliminarActionPerformed);

        botonActualizar.setText("Actualizar");
        botonActualizar.addActionListener(this::botonActualizarActionPerformed);

        botonCerrar.setText("Cerrar");
        botonCerrar.addActionListener(this::botonCerrarActionPerformed);

        tituloControl.setFont(tituloControl.getFont().deriveFont(16f));
        tituloControl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloControl.setText("Control de asistencia");

        tablaReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        panelTablaReporte.setViewportView(tablaReporte);

        botonAtrasos.setText("Reporte de atrasos");
        botonAtrasos.addActionListener(this::botonAtrasosActionPerformed);

        botonSalidas.setText("Reporte de salidas anticipadas");
        botonSalidas.addActionListener(this::botonSalidasActionPerformed);

        botonInasistencias.setText("Reporte de inasistencias");
        botonInasistencias.addActionListener(this::botonInasistenciasActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tituloVentana, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTabla)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonCrear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonCerrar)
                        .addGap(0, 225, Short.MAX_VALUE))
                    .addComponent(tituloControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTablaReporte)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonAtrasos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonSalidas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonInasistencias)
                        .addGap(0, 294, Short.MAX_VALUE)))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(tituloVentana)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCrear)
                    .addComponent(botonModificar)
                    .addComponent(botonEliminar)
                    .addComponent(botonActualizar)
                    .addComponent(botonCerrar))
                .addGap(18, 18, 18)
                .addComponent(tituloControl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTablaReporte, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAtrasos)
                    .addComponent(botonSalidas)
                    .addComponent(botonInasistencias))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCrearActionPerformed
        crearUsuario();
    }//GEN-LAST:event_botonCrearActionPerformed

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed
        modificarUsuario();
    }//GEN-LAST:event_botonModificarActionPerformed

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed
        eliminarUsuario();
    }//GEN-LAST:event_botonEliminarActionPerformed

    private void botonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarActionPerformed
        cargarUsuarios();
    }//GEN-LAST:event_botonActualizarActionPerformed

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_botonCerrarActionPerformed

    private void botonAtrasosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAtrasosActionPerformed
        cargarReporte("Reporte de atrasos", () -> reportesDatos.obtenerAtrasos());
    }//GEN-LAST:event_botonAtrasosActionPerformed

    private void botonSalidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalidasActionPerformed
        cargarReporte("Reporte de salidas anticipadas", () -> reportesDatos.obtenerSalidasAnticipadas());
    }//GEN-LAST:event_botonSalidasActionPerformed

    private void botonInasistenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInasistenciasActionPerformed
        cargarInasistencias();
    }//GEN-LAST:event_botonInasistenciasActionPerformed

    private void cargarUsuarios() {
        try {
            tablaUsuarios.setModel(usuariosDatos.obtenerTablaUsuarios());
        } catch (SQLException e) {
            Mensajes.mostrarErrorConexion(this);
        }
    }

    private void cargarInasistencias() {
        String textoFecha = JOptionPane.showInputDialog(
                this,
                "Ingrese fecha a consultar con formato AAAA-MM-DD:",
                LocalDate.now().toString()
        );
        if (textoFecha == null) {
            return;
        }

        try {
            LocalDate fecha = LocalDate.parse(textoFecha.trim());
            cargarReporte("Reporte de inasistencias", () -> reportesDatos.obtenerInasistencias(fecha));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Fecha mal escrita.");
        }
    }

    private void cargarReporte(String titulo, CargaReporte cargaReporte) {
        try {
            DefaultTableModel modelo = cargaReporte.cargar();
            tablaReporte.setModel(modelo);
            tituloControl.setText(titulo + " (" + modelo.getRowCount() + " registros)");
        } catch (SQLException e) {
            Mensajes.mostrarErrorConexion(this);
        }
    }

    private void crearUsuario() {
        DatosFormulario datos = mostrarFormulario("Crear usuario", null);
        if (datos == null) {
            return;
        }
        if (datos.clave.isBlank()) {
            JOptionPane.showMessageDialog(this, "Falta poner la contrasena.");
            return;
        }

        try {
            usuariosDatos.crearUsuario(datos.nombre, datos.correo, datos.clave, datos.rol);
            cargarUsuarios();
            JOptionPane.showMessageDialog(this, "Usuario guardado.");
        } catch (SQLException e) {
            Mensajes.mostrarErrorConexion(this);
        }
    }

    private void modificarUsuario() {
        int fila = obtenerFilaSeleccionada();
        if (fila < 0) {
            return;
        }

        DatosFormulario original = new DatosFormulario(
                tablaUsuarios.getModel().getValueAt(fila, 1).toString(),
                tablaUsuarios.getModel().getValueAt(fila, 2).toString(),
                tablaUsuarios.getModel().getValueAt(fila, 3).toString(),
                ""
        );

        DatosFormulario datos = mostrarFormulario("Modificar usuario", original);
        if (datos == null) {
            return;
        }

        int id = (int) tablaUsuarios.getModel().getValueAt(fila, 0);
        try {
            usuariosDatos.modificarUsuario(id, datos.nombre, datos.correo, datos.rol, datos.clave);
            cargarUsuarios();
            JOptionPane.showMessageDialog(this, "Usuario cambiado.");
        } catch (SQLException e) {
            Mensajes.mostrarErrorConexion(this);
        }
    }

    private void eliminarUsuario() {
        int fila = obtenerFilaSeleccionada();
        if (fila < 0) {
            return;
        }

        int id = (int) tablaUsuarios.getModel().getValueAt(fila, 0);
        if (id == administradorActual.getId()) {
            JOptionPane.showMessageDialog(this, "Ese usuario no se puede quitar ahora.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "El usuario no podra iniciar sesion. Continuar?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            usuariosDatos.eliminarUsuario(id);
            cargarUsuarios();
            JOptionPane.showMessageDialog(this, "Usuario quitado.");
        } catch (SQLException e) {
            Mensajes.mostrarErrorConexion(this);
        }
    }

    private DatosFormulario mostrarFormulario(String titulo, DatosFormulario original) {
        JTextField campoNombre = new JTextField(original == null ? "" : original.nombre, 22);
        JTextField campoCorreo = new JTextField(original == null ? "" : original.correo, 22);
        JComboBox<String> campoRol = new JComboBox<>(new String[]{"USUARIO", "ADMIN"});
        if (original != null) {
            campoRol.setSelectedItem(original.rol);
        }
        JPasswordField campoClave = new JPasswordField(22);

        JPanel panel = new JPanel(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints posicion = new java.awt.GridBagConstraints();
        posicion.insets = new java.awt.Insets(5, 5, 5, 5);
        posicion.anchor = java.awt.GridBagConstraints.WEST;

        agregarFila(panel, posicion, 0, "Nombre:", campoNombre);
        agregarFila(panel, posicion, 1, "Correo:", campoCorreo);
        agregarFila(panel, posicion, 2, "Rol:", campoRol);
        agregarFila(panel, posicion, 3, original == null ? "Contrasena:" : "Nueva contrasena:", campoClave);

        if (original != null) {
            posicion.gridx = 1;
            posicion.gridy = 4;
            panel.add(new JLabel("Dejar vacio para mantener la actual."), posicion);
        }

        int opcion = JOptionPane.showConfirmDialog(this, panel, titulo, JOptionPane.OK_CANCEL_OPTION);
        if (opcion != JOptionPane.OK_OPTION) {
            return null;
        }

        String nombre = campoNombre.getText().trim();
        String correo = campoCorreo.getText().trim();
        String rol = campoRol.getSelectedItem().toString();
        String clave = new String(campoClave.getPassword());

        if (nombre.isBlank() || correo.isBlank()) {
            JOptionPane.showMessageDialog(this, "Falta nombre o correo.");
            return null;
        }

        return new DatosFormulario(nombre, correo, rol, clave);
    }

    private void agregarFila(JPanel panel, java.awt.GridBagConstraints posicion, int fila, String texto, java.awt.Component campo) {
        posicion.gridx = 0;
        posicion.gridy = fila;
        panel.add(new JLabel(texto), posicion);

        posicion.gridx = 1;
        panel.add(campo, posicion);
    }

    private int obtenerFilaSeleccionada() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Primero seleccione un usuario.");
            return -1;
        }
        return tablaUsuarios.convertRowIndexToModel(filaSeleccionada);
    }

    private static class DatosFormulario {
        private final String nombre;
        private final String correo;
        private final String rol;
        private final String clave;

        private DatosFormulario(String nombre, String correo, String rol, String clave) {
            this.nombre = nombre;
            this.correo = correo;
            this.rol = rol;
            this.clave = clave;
        }
    }

    private interface CargaReporte {
        DefaultTableModel cargar() throws SQLException;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonActualizar;
    private javax.swing.JButton botonAtrasos;
    private javax.swing.JButton botonCerrar;
    private javax.swing.JButton botonCrear;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonInasistencias;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonSalidas;
    private javax.swing.JScrollPane panelTabla;
    private javax.swing.JScrollPane panelTablaReporte;
    private javax.swing.JTable tablaReporte;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JLabel tituloControl;
    private javax.swing.JLabel tituloVentana;
    // End of variables declaration//GEN-END:variables
}
