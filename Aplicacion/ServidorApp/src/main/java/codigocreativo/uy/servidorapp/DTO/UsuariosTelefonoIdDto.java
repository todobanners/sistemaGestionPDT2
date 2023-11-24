package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.UsuariosTelefonoId}
 */
public class UsuariosTelefonoIdDto implements Serializable {
    private Long idUsuario;
    private String numero;

    public UsuariosTelefonoIdDto() {
    }

    public UsuariosTelefonoIdDto(Long idUsuario, String numero) {
        this.idUsuario = idUsuario;
        this.numero = numero;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public UsuariosTelefonoIdDto setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public UsuariosTelefonoIdDto setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuariosTelefonoIdDto entity = (UsuariosTelefonoIdDto) o;
        return Objects.equals(this.idUsuario, entity.idUsuario) &&
                Objects.equals(this.numero, entity.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, numero);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idUsuario = " + idUsuario + ", " +
                "numero = " + numero + ")";
    }
}