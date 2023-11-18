package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class BajaUbicacionBean implements BajaUbicacionRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    //Se crea la primer implementacion Registro de ubicaciones
    @Override
    public void crearBajaUbicacion(Ubicacion ubi) throws ServiciosException {
        try {
            em.persist(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo crear la baja de ubicacion");
        }
    }

    @Override
    public List<BajaUbicacion> listarBajaUbicaciones() throws ServiciosException {
        return em.createQuery("SELECT u FROM BajaUbicacion u", BajaUbicacion.class).getResultList();
    }

}