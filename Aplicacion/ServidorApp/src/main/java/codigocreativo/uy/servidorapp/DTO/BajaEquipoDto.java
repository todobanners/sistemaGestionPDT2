package codigocreativo.uy.servidorapp.DTO;

import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.BajaEquipo}
 */
public class BajaEquipoDto implements Serializable {
    private Long id;
    private String razon;
    private LocalDate fecha;
    private Estados estado;
    private String comentarios;

    public BajaEquipoDto() {
    }

    public BajaEquipoDto(Long id, String razon, LocalDate fecha, Estados estado, String comentarios) {
        this.id = id;
        this.razon = razon;
        this.fecha = fecha;
        this.estado = estado;
        this.comentarios = comentarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
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
        BajaEquipoDto entity = (BajaEquipoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.razon, entity.razon) &&
                Objects.equals(this.fecha, entity.fecha) &&
                Objects.equals(this.estado, entity.estado) &&
                Objects.equals(this.comentarios, entity.comentarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, razon, fecha, estado, comentarios);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "razon = " + razon + ", " +
                "fecha = " + fecha + ", " +
                "estado = " + estado + ", " +
                "comentarios = " + comentarios + ")";
    }
}