package codigocreativo.uy.servidorapp.DTO;

import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Perfil}
 */
public class PerfilDto implements Serializable {
    private Long id;
    private String nombrePerfil;
    private Estados estado;

    public PerfilDto() {
    }

    public PerfilDto(Long id, String nombrePerfil, Estados estado) {
        this.id = id;
        this.nombrePerfil = nombrePerfil;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
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
        PerfilDto entity = (PerfilDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombrePerfil, entity.nombrePerfil) &&
                Objects.equals(this.estado, entity.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombrePerfil, estado);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombrePerfil = " + nombrePerfil + ", " +
                "estado = " + estado + ")";
    }
}