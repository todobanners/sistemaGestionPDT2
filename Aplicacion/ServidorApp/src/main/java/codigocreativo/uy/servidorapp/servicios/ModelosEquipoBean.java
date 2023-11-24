package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.DTOMappers.ModelosEquipoMapper;
import codigocreativo.uy.servidorapp.entidades.ModelosEquipo;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class ModelosEquipoBean implements ModelosEquipoRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    @Inject
    private ModelosEquipoMapper ModelosEquipoMapper;


    @Override
    public void crearModelosEquipo(ModelosEquipoDto modelosEquipo) {
        ModelosEquipo modelosEquipoEntity = ModelosEquipoMapper.toEntity(modelosEquipo);
        em.persist(modelosEquipoEntity);
        em.flush();
    }

    /*@Override
    public void modificarModelosEquipo(ModelosEquipo modelosEquipo) {
        em.merge(modelosEquipo);
        em.flush();
    }

    @Override
    public void obtenerModelosEquipo(Long id) {
        em.find(ModelosEquipo.class, id);
    }*/

    @Override
    public List<ModelosEquipoDto> listarModelosEquipo() {
        List<ModelosEquipo> modelosEquipo = em.createQuery("SELECT m FROM ModelosEquipo m", ModelosEquipo.class).getResultList();
        return ModelosEquipoMapper.toDto(modelosEquipo);
    }
}

