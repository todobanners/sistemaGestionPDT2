package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.EquipoDto;
import codigocreativo.uy.servidorapp.DTO.UbicacionDto;
import codigocreativo.uy.servidorapp.DTOMappers.EquipoMapper;
import codigocreativo.uy.servidorapp.DTOMappers.UbicacionMapper;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
public class UbicacionBean implements UbicacionRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;

    @Inject
    private UbicacionMapper ubicacionMapper;

    @Inject
    private EquipoMapper equipoMapper;

    @Override
    public void crearUbicacion(UbicacionDto ubi) throws ServiciosException {
        try {
            em.persist(ubicacionMapper.toEntity(ubi));
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo crear la ubicacion");
        }
    }

    @Override
    public void modificarUbicacion(UbicacionDto ubi) throws ServiciosException {
        try {
            em.merge(ubicacionMapper.toEntity(ubi));
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiciosException("No se pudo modificar la ubicacion");
        }
    }

    @Override
    @Transactional
    public void borrarUbicacion(Long id) throws ServiciosException {
        try {
            em.remove(ubicacionMapper.toEntity(obtenerUbicacionPorId(id)));
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiciosException("No se pudo borrar la ubicacion");
        }
    }

    @Override
    public void moverEquipoDeUbicacion(EquipoDto equipo, UbicacionDto ubicacion) throws ServiciosException {
        try {
            equipo.setIdUbicacion(ubicacion);
            em.merge(equipoMapper.toEntity(equipo));
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo mover el equipo");
        }
    }

    @Override
    public List<UbicacionDto> listarUbicaciones() throws ServiciosException {
        List<Ubicacion> ubicaciones = em.createQuery("SELECT u FROM Ubicacion u", Ubicacion.class).getResultList();
        return ubicacionMapper.toDto(ubicaciones);
    }

    @Override
    public UbicacionDto obtenerUbicacionPorId(Long id) throws ServiciosException {
        return ubicacionMapper.toDto(em.createQuery("SELECT u FROM Ubicacion u WHERE u.id = :id", Ubicacion.class).setParameter("id", id).getSingleResult());
    }
}