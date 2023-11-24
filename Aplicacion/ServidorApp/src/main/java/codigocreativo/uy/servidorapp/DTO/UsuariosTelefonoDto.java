package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.UsuariosTelefono}
 */
public class UsuariosTelefonoDto implements Serializable {
    private UsuarioDto idUsuario;

    public UsuariosTelefonoDto() {
    }

    public UsuariosTelefonoDto(UsuarioDto idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UsuarioDto getIdUsuario() {
        return idUsuario;
    }

    public UsuariosTelefonoDto setIdUsuario(UsuarioDto idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuariosTelefonoDto entity = (UsuariosTelefonoDto) o;
        return Objects.equals(this.idUsuario, entity.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idUsuario = " + idUsuario + ")";
    }
}