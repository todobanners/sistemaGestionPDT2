package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.PermisoDto;
import codigocreativo.uy.servidorapp.DTOMappers.PermisoMapper;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class PermisoBean implements PermisoRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    //inyecto el dto mapper
    @Inject
    PermisoMapper permisoMapper;
    @Override
    public void crearPermiso(PermisoDto p) {
        Permiso permisoEntity = permisoMapper.toEntity(p);
        em.persist(permisoEntity);
        em.flush();
    }

    @Override
    public void modificarPermiso(PermisoDto p) {
        Permiso permisoEntity = permisoMapper.toEntity(p);
        em.merge(permisoEntity);
        em.flush();
    }

    @Override
    public void eliminarPermiso(PermisoDto p) {
        Permiso permisoEntity = permisoMapper.toEntity(p);
        em.remove(permisoEntity);
        em.flush();
    }

    @Override
    public PermisoDto obtenerPermiso(Long id) {
        Permiso permiso = em.find(Permiso.class, id);
        return permisoMapper.toDto(permiso);
    }

    @Override
    public List<PermisoDto> obtenerPermisos() {
        List<Permiso> permisos = em.createQuery("SELECT p FROM Permiso p", Permiso.class).getResultList();
        return permisoMapper.toDto(permisos);
    }
}
