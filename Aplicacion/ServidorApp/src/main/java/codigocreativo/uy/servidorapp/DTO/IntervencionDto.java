package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Intervencion}
 */
public class IntervencionDto implements Serializable {
    private Long id;
    private String motivo;
    private LocalDate fechaHora;
    private String comentarios;

    public IntervencionDto() {
    }

    public IntervencionDto(Long id, String motivo, LocalDate fechaHora, String comentarios) {
        this.id = id;
        this.motivo = motivo;
        this.fechaHora = fechaHora;
        this.comentarios = comentarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntervencionDto entity = (IntervencionDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.motivo, entity.motivo) &&
                Objects.equals(this.fechaHora, entity.fechaHora) &&
                Objects.equals(this.comentarios, entity.comentarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, motivo, fechaHora, comentarios);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "motivo = " + motivo + ", " +
                "fechaHora = " + fechaHora + ", " +
                "comentarios = " + comentarios + ")";
    }
}