package entidades;
public class Usuario {
    private final int id;
    private final String nombre;
    private final String correo;
    private final String rol;
    private final boolean activo;
    public Usuario(int id, String nombre, String correo, String rol) {
        this(id, nombre, correo, rol, true);
    }
    public Usuario(int id, String nombre, String correo, String rol, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.activo = activo;
    }
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public String getRol() {
        return rol;
    }
    public boolean isActivo() {
        return activo;
    }
    public boolean esAdministrador() {
        return "ADMIN".equalsIgnoreCase(rol);
    }
    @Override
    public String toString() {
        return nombre + " - " + correo;
    }
}
