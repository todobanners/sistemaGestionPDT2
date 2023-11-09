package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Intervencion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import javax.swing.*;
import java.util.List;
@Stateless
public class IntervencionBean implements IntervencionRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;

    @Override
    public Intervencion crear(Intervencion intervencion) throws ServiciosException {
        em.persist(intervencion);
        em.flush();
        return intervencion;
    }

    @Override
    public void actualizar(Intervencion intervencion) throws ServiciosException {
        em.merge(intervencion);
        em.flush();
    }

    @Override
    public void eliminar(Long id) throws ServiciosException {
        Intervencion intervencion = em.find(Intervencion.class, id);
        em.remove(intervencion);
        em.flush();
    }

    @Override
    public Intervencion obtenerPorId(Long id) throws ServiciosException {
        return em.find(Intervencion.class, id);
    }

    @Override
    public List<Intervencion> obtenerTodas() throws ServiciosException {
        return em.createQuery("SELECT i FROM Intervencion i", Intervencion.class).getResultList();
    }
}
