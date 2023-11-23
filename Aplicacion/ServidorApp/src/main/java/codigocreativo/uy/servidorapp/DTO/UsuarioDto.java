package codigocreativo.uy.servidorapp.DTO;

import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Usuario}
 */
public class UsuarioDto implements Serializable {
    private Long id;
    private String cedula;
    private String email;
    private String contrasenia;
    private LocalDate fechaNacimiento;
    private Estados estado;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private InstitucionDto idInstitucion;
    private PerfilDto idPerfil;

    public UsuarioDto() {
    }

    public UsuarioDto(Long id, String cedula, String email, String contrasenia, LocalDate fechaNacimiento, Estados estado, String nombre, String apellido, String nombreUsuario, InstitucionDto idInstitucion, PerfilDto idPerfil) {
        this.id = id;
        this.cedula = cedula;
        this.email = email;
        this.contrasenia = contrasenia;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.idInstitucion = idInstitucion;
        this.idPerfil = idPerfil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDto entity = (UsuarioDto) o;
        return Objects.equals(this.id, entity.id) && Objects.equals(this.cedula, entity.cedula) && Objects.equals(this.email, entity.email) && Objects.equals(this.contrasenia, entity.contrasenia) && Objects.equals(this.fechaNacimiento, entity.fechaNacimiento) && Objects.equals(this.estado, entity.estado) && Objects.equals(this.nombre, entity.nombre) && Objects.equals(this.apellido, entity.apellido) && Objects.equals(this.nombreUsuario, entity.nombreUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cedula, email, contrasenia, fechaNacimiento, estado, nombre, apellido, nombreUsuario);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + "id = " + id + ", " + "cedula = " + cedula + ", " + "email = " + email + ", " + "contrasenia = " + contrasenia + ", " + "fechaNacimiento = " + fechaNacimiento + ", " + "estado = " + estado + ", " + "nombre = " + nombre + ", " + "apellido = " + apellido + ", " + "nombreUsuario = " + nombreUsuario + ")";
    }

    public InstitucionDto getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(InstitucionDto idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public PerfilDto getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(PerfilDto idPerfil) {
        this.idPerfil = idPerfil;
    }

}