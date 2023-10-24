package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsuariosTelefonoId implements Serializable {
    private static final long serialVersionUID = 7977674427591514437L;
    @Column(name = "ID_USUARIO", nullable = false)
    private Long idUsuario;

    @Column(name = "NUMERO", nullable = false, length = 20)
    private String numero;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsuariosTelefonoId entity = (UsuariosTelefonoId) o;
        return Objects.equals(this.numero, entity.numero) &&
                Objects.equals(this.idUsuario, entity.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, idUsuario);
    }

}