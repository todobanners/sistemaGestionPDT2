package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.UsuariosTelefono}
 */
public class UsuariosTelefonoDto implements Serializable {
    private UsuariosTelefonoIdDto id;
    private UsuarioDto idUsuario;

    public UsuariosTelefonoDto() {
    }

    public UsuariosTelefonoDto(
            UsuariosTelefonoIdDto id, UsuarioDto idUsuario) {
        this.idUsuario = idUsuario;
        this.id = id;
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

    public UsuariosTelefonoIdDto getId() {
        return id;
    }

    public UsuariosTelefonoDto setId(UsuariosTelefonoIdDto id) {
        this.id = id;
        return this;
    }
}