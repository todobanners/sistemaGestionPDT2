package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "UBICACIONES")
public class Ubicaciones implements java.io.Serializable{
    private static final long serialVersionUID = 4L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_UBICACION", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "SECTOR", length = 30)
    private String sector;

    @Column(name = "PISO")
    private Long piso;

    @Column(name = "NUMERO")
    private Long numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_INSTITUCION")
    private codigocreativo.uy.servidorapp.entidades.Instituciones idInstitucion;

    @OneToMany(mappedBy = "idUbicacion")
    private Set<codigocreativo.uy.servidorapp.entidades.Equipos> equipos = new LinkedHashSet<>();

    public Ubicaciones() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Long getPiso() {
        return piso;
    }

    public void setPiso(Long piso) {
        this.piso = piso;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public codigocreativo.uy.servidorapp.entidades.Instituciones getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(codigocreativo.uy.servidorapp.entidades.Instituciones idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Set<codigocreativo.uy.servidorapp.entidades.Equipos> getEquipos() {
        return equipos;
    }

    public void setEquipos(Set<codigocreativo.uy.servidorapp.entidades.Equipos> equipos) {
        this.equipos = equipos;
    }

    public Ubicaciones(Long id, String nombre, String sector, Long piso, Long numero, Instituciones idInstitucion, Set<Equipos> equipos) {
        this.id = id;
        this.nombre = nombre;
        this.sector = sector;
        this.piso = piso;
        this.numero = numero;
        this.idInstitucion = idInstitucion;
        this.equipos = equipos;
    }
}