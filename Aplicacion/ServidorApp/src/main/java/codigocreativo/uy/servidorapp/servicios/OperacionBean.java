package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Operacion;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless

public class OperacionBean implements OperacionRemote{
    @PersistenceContext
    private EntityManager em;
    @Override
    public void crearOperacion(Operacion o) {
        em.persist(o);
        em.flush();
    }

    @Override
    public void modificarOperacion(Operacion o) {
        em.merge(o);
        em.flush();

    }

    @Override
    public void eliminarOperacion(Operacion o) {
        em.remove(o);

    }
}
