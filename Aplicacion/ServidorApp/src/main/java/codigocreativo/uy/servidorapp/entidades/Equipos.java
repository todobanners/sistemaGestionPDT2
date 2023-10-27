package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "EQUIPOS")
public class Equipos implements java.io.Serializable {
    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EQUIPO", nullable = false)
    private Long id;

    @Column(name = "ID_INTERNO", nullable = false, length = 50)
    private String idInterno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_UBICACION", nullable = false)
    private codigocreativo.uy.servidorapp.entidades.Ubicaciones idUbicacion;

    @Column(name = "NRO_SERIE", nullable = false, length = 100)
    private String nroSerie;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Lob
    @Column(name = "IMAGEN")
    private String imagen;

    @Column(name = "FECHA_ADQUISICION")
    private LocalDate fechaAdquisicion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(String idInterno) {
        this.idInterno = idInterno;
    }

    public codigocreativo.uy.servidorapp.entidades.Ubicaciones getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(codigocreativo.uy.servidorapp.entidades.Ubicaciones idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

}