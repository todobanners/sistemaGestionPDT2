package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class InstitucionBean implements InstitucionRemote{

    @PersistenceContext (unitName = "default")
    private EntityManager em;

    @Override
    public void agregarInstitucion(Institucion i) {
        em.persist(i);
        em.flush();
    }

    @Override
    public void eliminarInstitucion(Institucion i) {
        em.remove (i);

    }
    @Override
    public void modificarInstitucion(Institucion i) {
        em.persist(i);
        em.flush();

    }

    @Override
    public List obtenerUbicaciones() {
        return em.createQuery("SELECT Ubicacion FROM Institucion i, Institucion.class").getResultList();
    }

    @Override
    public List<Institucion> obtenerInstituciones() {
        return em.createQuery("SELECT i FROM Institucion i", Institucion.class).getResultList();
    }

    @Override
    public Institucion obtenerInstitucionPorNombre(String nombre) {
        return em.createQuery("SELECT i FROM Institucion i WHERE i.nombre = :nombre", Institucion.class).setParameter("nombre", nombre).getSingleResult();
    }

    @Override
    public Institucion obtenerInstitucionPorId(Long id) {
        return em.createQuery("SELECT i FROM Institucion i WHERE i.id = :id", Institucion.class).setParameter("id", id).getSingleResult();
    }
}
