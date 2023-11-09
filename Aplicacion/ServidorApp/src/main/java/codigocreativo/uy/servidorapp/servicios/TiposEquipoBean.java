package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Pais;
import codigocreativo.uy.servidorapp.entidades.TiposEquipo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class TiposEquipoBean implements TiposEquipoRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;


    @Override
    public void crearTiposEquipo(TiposEquipo tiposEquipo) {
        em.persist(tiposEquipo);
        em.flush();
    }

    @Override
    public void modificarTiposEquipo(TiposEquipo tiposEquipo) {
        em.merge(tiposEquipo);
        em.flush();
    }

    @Override
    public void obtenerTiposEquipo(Long id) {
        em.find(TiposEquipo.class, id);
    }


    @Override
    public List<TiposEquipo> obtenerTiposEquipo() {
        return em.createQuery("SELECT TiposEquipo FROM TiposEquipo tiposEquipo", TiposEquipo.class).getResultList();
    }
}

