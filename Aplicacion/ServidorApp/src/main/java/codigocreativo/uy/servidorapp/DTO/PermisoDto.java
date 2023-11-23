package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Permiso}
 */
public class PermisoDto implements Serializable {
    private Long id;
    private String tipoPermiso;

    public PermisoDto() {
    }

    public PermisoDto(Long id, String tipoPermiso) {
        this.id = id;
        this.tipoPermiso = tipoPermiso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermisoDto entity = (PermisoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.tipoPermiso, entity.tipoPermiso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoPermiso);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "tipoPermiso = " + tipoPermiso + ")";
    }
}