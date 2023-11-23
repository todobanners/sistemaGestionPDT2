package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Ubicacion}
 */
public class UbicacionDto implements Serializable {
    private Long id;
    private String nombre;
    private String sector;
    private Long piso;
    private Long numero;
    private Long cama;
    private InstitucionDto idInstitucion;

    public UbicacionDto() {
    }

    public UbicacionDto(Long id, String nombre, String sector, Long piso, Long numero, Long cama,
                        InstitucionDto idInstitucion) {
        this.id = id;
        this.nombre = nombre;
        this.sector = sector;
        this.piso = piso;
        this.numero = numero;
        this.cama = cama;
        this.idInstitucion = idInstitucion;
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

    public Long getCama() {
        return cama;
    }

    public void setCama(Long cama) {
        this.cama = cama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UbicacionDto entity = (UbicacionDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombre, entity.nombre) &&
                Objects.equals(this.sector, entity.sector) &&
                Objects.equals(this.piso, entity.piso) &&
                Objects.equals(this.numero, entity.numero) &&
                Objects.equals(this.cama, entity.cama);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, sector, piso, numero, cama);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombre = " + nombre + ", " +
                "sector = " + sector + ", " +
                "piso = " + piso + ", " +
                "numero = " + numero + ", " +
                "cama = " + cama + ")";
    }

    public InstitucionDto getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(InstitucionDto idInstitucion) {
        this.idInstitucion = idInstitucion;
    }
}