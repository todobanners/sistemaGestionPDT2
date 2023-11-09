package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.PerfilesPermiso;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class PerfilesPermisoBean implements PerfilesPermisoRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;


    @Override
    public void CrearPerfilesPermiso(PerfilesPermiso perfilesPermiso) {
        em.persist(perfilesPermiso);
        em.flush();
    }

    @Override
    public void modificarPerfilesPermiso(PerfilesPermiso perfilesPermiso) {
        em.merge(perfilesPermiso);
        em.flush();
    }

    @Override
    public List<PerfilesPermiso> obtenerPerfilesPermiso() {
        return em.createQuery("SELECT PerfilesPermiso FROM PerfilesPermiso perfilesPermiso", PerfilesPermiso.class).getResultList();
    }
}

