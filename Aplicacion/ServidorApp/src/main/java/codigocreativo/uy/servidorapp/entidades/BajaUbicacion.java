package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "BAJA_UBICACIONES")
public class BajaUbicacion implements Serializable {
    @Id
    @Column(name = "ID_BAJA", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_INSTITUCIONES", nullable = false)
    private Institucion idInstituciones;

    @Column(name = "RAZON", nullable = false, length = 30)
    private String razon;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_UBICACIONES", nullable = false)
    private Institucion idUbicaciones;;

    @Column(name = "COMENTARIOS", nullable = false, length = 100)
    private String comentarios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Institucion getIdInstituciones() {
        return idInstituciones;
    }

    public void setIdInstituciones(Institucion idInstituciones) {
        this.idInstituciones = idInstituciones;
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

    public Institucion getIdUbicaciones() {
        return idUbicaciones;
    }

    public void setIdUbicaciones(Institucion idUbicaciones) {
        this.idUbicaciones = idUbicaciones;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}