package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Equipos;
import codigocreativo.uy.servidorapp.entidades.Ubicaciones;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UbicacionBean implements UbicacionRemote {
    @PersistenceContext
    private EntityManager em;
    //Se crea la primer implementacion Registro de ubicaciones
    @Override
    public void crearUbicacion(Ubicaciones ubi) throws ServiciosException {
        try {
            em.persist(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo crear la ubicacion");
        }
    }

    @Override
    public void modificarUbicacion(Ubicaciones ubi) throws ServiciosException {
        try {
            em.merge(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo modificar la ubicacion");
        }
    }

    @Override
    public void modificarUbicacionPorId(Long id) throws ServiciosException {
        try {
            Ubicaciones ubi = em.find(Ubicaciones.class, id);
            em.merge(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo modificar la ubicacion");
        }
    }

    @Override
    public void modificarUbicacionPorNombre(String nombre) throws ServiciosException {
        try {
            Ubicaciones ubi = em.find(Ubicaciones.class, nombre);
            em.merge(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo modificar la ubicacion");
        }
    }

    @Override
    public void borrarUbicacion(Long id) throws ServiciosException {
        try {
            Ubicaciones ubi = em.find(Ubicaciones.class, id);
            em.remove(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo borrar la ubicacion");
        }
    }

    @Override
    public void moverEquipoDeUbicacion(Equipos equipo, Ubicaciones ubicacion) throws ServiciosException {
        try {
            equipo.setIdUbicacion(ubicacion);
            em.merge(equipo);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo mover el equipo");
        }
    }

}
