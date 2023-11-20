package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
public class UbicacionBean implements UbicacionRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    //Se crea la primer implementacion Registro de ubicaciones
    @Override
    public void crearUbicacion(Ubicacion ubi) throws ServiciosException {
        try {
            em.persist(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo crear la ubicacion");
        }
    }

    @Override
    public void modificarUbicacion(Ubicacion ubi) throws ServiciosException {
        try {
            em.merge(ubi);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiciosException("No se pudo modificar la ubicacion");
        }
    }

    @Override
    public void modificarUbicacionPorId(Long id) throws ServiciosException {
        try {
            Ubicacion ubi = em.find(Ubicacion.class, id);
            em.merge(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo modificar la ubicacion");
        }
    }

    @Override
    public void modificarUbicacionPorNombre(String nombre) throws ServiciosException {
        try {
            Ubicacion ubi = em.find(Ubicacion.class, nombre);
            em.merge(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo modificar la ubicacion");
        }
    }

    @Override
    @Transactional
    public void borrarUbicacion(Long id) throws ServiciosException {
        try {
            Ubicacion ubi = em.find(Ubicacion.class, id);
            em.remove(ubi);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiciosException("No se pudo borrar la ubicacion");
        }
    }

    @Override
    public void moverEquipoDeUbicacion(Equipo equipo, Long ubicacionId) throws ServiciosException {
        try {
            Ubicacion ubicacion = em.find(Ubicacion.class, ubicacionId);
            equipo.setIdUbicacion(ubicacion);
            em.merge(equipo);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo mover el equipo");
        }
    }

    @Override
    public List<Ubicacion> listarUbicaciones() throws ServiciosException {
        return em.createQuery("SELECT u FROM Ubicacion u", Ubicacion.class).getResultList();
    }

    @Override
    public Ubicacion obtenerUbicacionPorId(Long id) throws ServiciosException {
        return em.createQuery("SELECT u FROM Ubicacion u WHERE u.id = :id", Ubicacion.class).setParameter("id", id).getSingleResult();

        //return em.createQuery("SELECT u FROM Ubicacion u WHERE u.id = :id", Ubicacion.class).setParameter("id", id).getSingleResult().getNombre();
    }

}
