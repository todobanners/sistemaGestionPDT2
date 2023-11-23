package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Auditoria}
 */
public class AuditoriaDto implements Serializable {
    private Long id;
    private LocalDate fechaHora;

    public AuditoriaDto() {
    }

    public AuditoriaDto(Long id, LocalDate fechaHora) {
        this.id = id;
        this.fechaHora = fechaHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditoriaDto entity = (AuditoriaDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.fechaHora, entity.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fechaHora);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "fechaHora = " + fechaHora + ")";
    }
}