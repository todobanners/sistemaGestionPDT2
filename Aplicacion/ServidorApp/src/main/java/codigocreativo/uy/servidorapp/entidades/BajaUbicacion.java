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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BAJA", nullable = false)
    private Long id;


    @OnDelete(action = OnDeleteAction.RESTRICT)
    @Column(name = "ID_USUARIO")
    private long idUsuario;

    @OnDelete(action = OnDeleteAction.RESTRICT)
    @Column(name = "ID_INSTITUCION")
    private long idInstitucion;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "ID_UBICACIONES")
    private long idUbicacion;

    @Column(name = "RAZON")
    private String razon;

    @Column(name = "COMENTARIO")
    private String comentario;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}