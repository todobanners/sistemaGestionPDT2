package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.MarcasModelo}
 */
public class MarcasModeloDto implements Serializable {
    private Long id;
    private String nombre;
    private Set<ModelosEquipoDto> modelosEquipos = new LinkedHashSet<>();

    public MarcasModeloDto() {
    }

    public MarcasModeloDto(Long id, String nombre,
                           Set<ModelosEquipoDto> modelosEquipos) {
        this.id = id;
        this.nombre = nombre;
        this.modelosEquipos = modelosEquipos;
    }

    public Long getId() {
        return id;
    }

    public MarcasModeloDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public MarcasModeloDto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarcasModeloDto entity = (MarcasModeloDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombre, entity.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombre = " + nombre + ")";
    }

    public Set<ModelosEquipoDto> getModelosEquipos() {
        return modelosEquipos;
    }

    public MarcasModeloDto setModelosEquipos(Set<ModelosEquipoDto> modelosEquipos) {
        this.modelosEquipos = modelosEquipos;
        return this;
    }
}