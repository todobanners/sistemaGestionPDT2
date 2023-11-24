package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.BajaUbicacionDto;
import codigocreativo.uy.servidorapp.DTOMappers.BajaUbicacionMapper;
import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
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
    @Inject
    private BajaUbicacionMapper bajaUbicacionMapper;

    @Override
    @Transactional
    public void crearBajaUbicacion(BajaUbicacionDto bajaUbicacion) throws ServiciosException {
        try {
            BajaUbicacion bajaUbicacionEntity = bajaUbicacionMapper.toEntity(bajaUbicacion);
            em.persist(bajaUbicacionEntity);
            em.flush();
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
    public List<BajaUbicacionDto> listarBajaUbicaciones() throws ServiciosException {
        return em.createQuery("SELECT u FROM BajaUbicacion u", BajaUbicacion.class).getResultList();
    }
}
