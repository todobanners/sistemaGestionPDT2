package codigocreativo.uy.servidorapp.DTO;

import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.TiposIntervencione}
 */
public class TiposIntervencioneDto implements Serializable {
    private Long id;
    private String nombreTipo;
    private Estados estado;

    public TiposIntervencioneDto() {
    }

    public TiposIntervencioneDto(Long id, String nombreTipo, Estados estado) {
        this.id = id;
        this.nombreTipo = nombreTipo;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
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
        TiposIntervencioneDto entity = (TiposIntervencioneDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombreTipo, entity.nombreTipo) &&
                Objects.equals(this.estado, entity.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreTipo, estado);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombreTipo = " + nombreTipo + ", " +
                "estado = " + estado + ")";
    }
}