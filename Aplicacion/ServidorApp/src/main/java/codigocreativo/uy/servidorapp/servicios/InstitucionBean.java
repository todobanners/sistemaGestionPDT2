package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Instituciones;
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
    public Instituciones obtenerInstitucionPorNombre(String nombre) {
        return em.createQuery("SELECT i FROM Institucion i WHERE i.nombre = :nombre", Instituciones.class).setParameter("nombre", nombre).getSingleResult();
    }
}
