package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.ModelosEquipo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class ModelosEquipoBean implements ModelosEquipoRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;


    @Override
    public void crearModelosEquipo(ModelosEquipo modelosEquipo) {
        em.persist(modelosEquipo);
        em.flush();
    }

    @Override
    public void modificarModelosEquipo(ModelosEquipo modelosEquipo) {
        em.merge(modelosEquipo);
        em.flush();
    }

    @Override
    public void obtenerModelosEquipo(Long id) {
        em.find(ModelosEquipo.class, id);
    }


    @Override
    public List<ModelosEquipo> listarModelosEquipo() {
        return em.createQuery("SELECT ModelosEquipo FROM ModelosEquipo modelosEquipo", ModelosEquipo.class).getResultList();
    }
}

