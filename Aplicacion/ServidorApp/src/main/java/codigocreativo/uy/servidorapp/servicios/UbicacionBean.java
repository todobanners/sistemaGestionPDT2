package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class UbicacionBean implements UbicacionRemote {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void crearUbicacion(Ubicacion ubicacion) {
        em.persist(ubicacion);
        em.flush();
    }

    @Override
    public void modificarUbicacion(Ubicacion ubicacion) {
        em.merge(ubicacion);
        em.flush();
    }

    @Override
    public void eliminarUbicacion(Ubicacion ubicacion) {
        em.remove(ubicacion);
        em.flush();
    }

    @Override
    public void obtenerUbicacion(Long id) {
        em.find(Ubicacion.class, id);
    }


    @Override
    public List<Ubicacion> obtenerUbicacion() {
        return em.createQuery("SELECT ubicacion FROM Ubicacion ubicacion", Ubicacion.class).getResultList();
    }
}

