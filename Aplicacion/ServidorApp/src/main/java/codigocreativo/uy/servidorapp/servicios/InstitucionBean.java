package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class InstitucionBean implements InstitucionRemote{

    @PersistenceContext
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
        return em.createQuery("SELECT Ubicaciones FROM Institucion i, Institucion.class").getResultList();
    }
}
