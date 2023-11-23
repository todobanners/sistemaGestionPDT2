package codigocreativo.uy.servidorapp.DTO;

import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Equipo}
 */
public class EquipoDto implements Serializable {
    private Long id;
    private String idInterno;
    private String nroSerie;
    private String nombre;
    private String imagen;
    private LocalDate fechaAdquisicion;
    private Estados estado;

    public EquipoDto() {
    }

    public EquipoDto(Long id, String idInterno, String nroSerie, String nombre, String imagen, LocalDate fechaAdquisicion, Estados estado) {
        this.id = id;
        this.idInterno = idInterno;
        this.nroSerie = nroSerie;
        this.nombre = nombre;
        this.imagen = imagen;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(String idInterno) {
        this.idInterno = idInterno;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipoDto entity = (EquipoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.idInterno, entity.idInterno) &&
                Objects.equals(this.nroSerie, entity.nroSerie) &&
                Objects.equals(this.nombre, entity.nombre) &&
                Objects.equals(this.imagen, entity.imagen) &&
                Objects.equals(this.fechaAdquisicion, entity.fechaAdquisicion) &&
                Objects.equals(this.estado, entity.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idInterno, nroSerie, nombre, imagen, fechaAdquisicion, estado);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idInterno = " + idInterno + ", " +
                "nroSerie = " + nroSerie + ", " +
                "nombre = " + nombre + ", " +
                "imagen = " + imagen + ", " +
                "fechaAdquisicion = " + fechaAdquisicion + ", " +
                "estado = " + estado + ")";
    }
}