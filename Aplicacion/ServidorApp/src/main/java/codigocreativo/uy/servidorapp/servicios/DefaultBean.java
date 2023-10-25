package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.DefaultEntidad;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class DefaultBean implements DefaultRemote {
    @PersistenceContext
    private EntityManager em;
    @Override
    public void create(DefaultEntidad d) throws ServiciosException {
        try {
            em.persist(d);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo crear el default");
        }
    }
    //Listar los defaults
    @Override
    public List<DefaultEntidad> list() throws ServiciosException {
        try {
            TypedQuery<DefaultEntidad> query = em.createQuery("SELECT d FROM DefaultEntidad d", DefaultEntidad.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo listar los defaults");
        }
    }

    @Override
    public void borrar(Long id) throws ServiciosException {
        try {
            DefaultEntidad d = em.find(DefaultEntidad.class, id);
            em.remove(d);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo borrar el default");
        }
    }

}
