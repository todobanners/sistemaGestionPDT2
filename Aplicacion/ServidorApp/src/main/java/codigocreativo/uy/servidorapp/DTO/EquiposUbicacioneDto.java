package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.EquiposUbicacione}
 */
public class EquiposUbicacioneDto implements Serializable {
    private Long id;
    private Long idUbicacion;
    private LocalDate fecha;

    public EquiposUbicacioneDto() {
    }

    public EquiposUbicacioneDto(Long id, Long idUbicacion, LocalDate fecha) {
        this.id = id;
        this.idUbicacion = idUbicacion;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquiposUbicacioneDto entity = (EquiposUbicacioneDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.idUbicacion, entity.idUbicacion) &&
                Objects.equals(this.fecha, entity.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUbicacion, fecha);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idUbicacion = " + idUbicacion + ", " +
                "fecha = " + fecha + ")";
    }
}