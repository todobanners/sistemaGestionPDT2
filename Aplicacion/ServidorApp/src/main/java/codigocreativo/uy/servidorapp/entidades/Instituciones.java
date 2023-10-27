package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "INSTITUCIONES")
public class Instituciones implements java.io.Serializable{
    private static final long serialVersionUID = 3L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSTITUCION", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 40)
    private String nombre;

    @OneToMany(mappedBy = "idInstitucion")
    private Set<codigocreativo.uy.servidorapp.entidades.Ubicaciones> ubicaciones = new LinkedHashSet<>();

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

    public Set<codigocreativo.uy.servidorapp.entidades.Ubicaciones> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(Set<codigocreativo.uy.servidorapp.entidades.Ubicaciones> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

}