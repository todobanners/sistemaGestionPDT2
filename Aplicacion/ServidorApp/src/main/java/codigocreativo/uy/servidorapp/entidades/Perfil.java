package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "PERFILES")
public class Perfil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERFIL", nullable = false)
    private Long id;

    @Column(name = "NOMBRE_PERFIL", nullable = false, length = 20)
    private String nombrePerfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false, length = 20)
    private Estados estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return id +" - " + nombrePerfil;
    }
}