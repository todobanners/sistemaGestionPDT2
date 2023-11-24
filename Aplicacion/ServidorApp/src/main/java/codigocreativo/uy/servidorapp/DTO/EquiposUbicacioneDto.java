package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.EquiposUbicacione}
 */
public class EquiposUbicacioneDto implements Serializable {
    private Long id;
    private EquipoDto idEquipo;
    private UbicacionDto idUbicacion;
    private LocalDate fecha;

    public EquiposUbicacioneDto() {
    }

    public EquiposUbicacioneDto(Long id,
                                EquipoDto idEquipo,
                                UbicacionDto idUbicacion, LocalDate fecha) {
        this.id = id;
        this.fecha = fecha;
        this.idEquipo = idEquipo;
        this.idUbicacion = idUbicacion;
    }

    public Long getId() {
        return id;
    }

    public EquiposUbicacioneDto setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public EquiposUbicacioneDto setFecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquiposUbicacioneDto entity = (EquiposUbicacioneDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.fecha, entity.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "fecha = " + fecha + ")";
    }

    public EquipoDto getIdEquipo() {
        return idEquipo;
    }

    public EquiposUbicacioneDto setIdEquipo(EquipoDto idEquipo) {
        this.idEquipo = idEquipo;
        return this;
    }

    public UbicacionDto getIdUbicacion() {
        return idUbicacion;
    }

    public EquiposUbicacioneDto setIdUbicacion(UbicacionDto idUbicacion) {
        this.idUbicacion = idUbicacion;
        return this;
    }
}