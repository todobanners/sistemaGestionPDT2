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
    BajaUbicacionMapper bajaUbicacionMapper;

    @Override
    public void crearBajaUbicacion(BajaUbicacionDto bajaUbicacion) throws ServiciosException {
        try {
            // Persistir la baja de ubicación
            em.persist(bajaUbicacionMapper.toEntity(bajaUbicacion));
            // Dar de baja lógica a la ubicación
            em.createQuery("UPDATE Ubicacion ubicacion SET ubicacion.estado = codigocreativo.uy.servidorapp.enumerados.Estados.INACTIVO WHERE ubicacion.id = :id")
                    .setParameter("id", bajaUbicacion.getIdUbicacion().getId())
                    .executeUpdate();
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
        try {
            return bajaUbicacionMapper.toDto(em.createQuery("SELECT bajaUbicacion FROM BajaUbicacion bajaUbicacion", BajaUbicacion.class).getResultList());
        } catch (Exception e) {
            throw new ServiciosException("No se pudo listar las bajas de ubicaciones");
        }
    }
}
