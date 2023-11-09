package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Permiso;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class PermisoBean implements PermisoRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    @Override
    public void crearPermiso(Permiso p) {
        em.persist(p);
        em.flush();
    }

    @Override
    public void modificarPermiso(Permiso p) {
        em.merge(p);
        em.flush();
    }

    @Override
    public void eliminarPermiso(Permiso p) {
        em.remove(p);
        em.flush();
    }

    @Override
    public void obtenerPermiso(Long id) {
        em.find(Permiso.class, id);
    }

    @Override
    public List<Permiso> obtenerPermisos() {
        return em.createQuery("SELECT p FROM Permiso p", Permiso.class).getResultList();
    }
}
