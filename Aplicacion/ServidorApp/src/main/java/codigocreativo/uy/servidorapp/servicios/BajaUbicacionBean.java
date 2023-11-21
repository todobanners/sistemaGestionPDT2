package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
public class BajaUbicacionBean implements BajaUbicacionRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    @Transactional
    public void crearBajaUbicacion(BajaUbicacion bajaUbicacion) throws ServiciosException {
        try {
            // Persistir la baja de ubicación
            em.persist(bajaUbicacion);
            em.flush();
            // Eliminar la ubicación de la lista de ubicaciones
        } catch (Exception e) {
            throw new ServiciosException("No se pudo crear la baja de ubicación");
        }
    }

    @Override
    public void borrarUbicacion(Long id) throws ServiciosException {
        try {
            Ubicacion ubi = em.find(Ubicacion.class, id);
            em.remove(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo borrar la ubicacion");
        }
    }

    @Override
    public List<BajaUbicacion> listarBajaUbicaciones() throws ServiciosException {
        return em.createQuery("SELECT u FROM BajaUbicacion u", BajaUbicacion.class).getResultList();
    }
}
