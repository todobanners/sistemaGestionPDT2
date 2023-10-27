package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "defaultin")
public class DefaultEntidad implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "campo_default_string", length = 50)
    private String campoDefaultString;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampoDefaultString() {
        return campoDefaultString;
    }

    public void setCampoDefaultString(String campoDefaultString) {
        this.campoDefaultString = campoDefaultString;
    }
}