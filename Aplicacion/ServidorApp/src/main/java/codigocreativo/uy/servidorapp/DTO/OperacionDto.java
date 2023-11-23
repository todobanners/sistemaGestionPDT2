package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Operacion}
 */
public class OperacionDto implements Serializable {
    private Long id;
    private String nombreOperacion;

    public OperacionDto() {
    }

    public OperacionDto(Long id, String nombreOperacion) {
        this.id = id;
        this.nombreOperacion = nombreOperacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreOperacion() {
        return nombreOperacion;
    }

    public void setNombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperacionDto entity = (OperacionDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombreOperacion, entity.nombreOperacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreOperacion);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombreOperacion = " + nombreOperacion + ")";
    }
}