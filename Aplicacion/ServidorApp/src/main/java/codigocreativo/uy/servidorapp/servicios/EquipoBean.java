package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.BajaEquipoDto;
import codigocreativo.uy.servidorapp.DTO.EquipoDto;
import codigocreativo.uy.servidorapp.DTOMappers.BajaEquipoMapper;
import codigocreativo.uy.servidorapp.DTOMappers.EquipoMapper;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class EquipoBean implements EquipoRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;

    @Inject
    EquipoMapper equipoMapper;

    @Inject
    BajaEquipoMapper bajaEquipoMapper;

    @Override
    public void crearEquipo(EquipoDto equipo) {
        em.persist(equipoMapper.toEntity(equipo));
        em.flush();
    }

    @Override
    public void modificarEquipo(EquipoDto equipo) {
        em.merge(equipoMapper.toEntity(equipo));
        em.flush();
    }

    @Override
    public void eliminarEquipo(BajaEquipoDto bajaEquipo) {
        em.persist(bajaEquipoMapper.toEntity(bajaEquipo));
        em.createQuery("UPDATE Equipo equipo SET equipo.estado = 'INACTIVO' WHERE equipo.id = :id")
                .setParameter("id", bajaEquipo.getIdEquipo().getId())
                .executeUpdate();
        em.flush();
    }

    @Override
    public List<EquipoDto> obtenerEquiposFiltrado(String filtro, String valor) {
        return equipoMapper.toDto(em.createQuery("SELECT e FROM Equipo e WHERE e." + filtro + " = :valor", Equipo.class)
                .setParameter("valor", valor)
                .getResultList());
    }

    @Override
    public EquipoDto obtenerEquipo(Long id) {
        return equipoMapper.toDto(em.find(Equipo.class, id));
    }



    @Override
    public List<EquipoDto> listarEquipos() {
        return equipoMapper.toDto(em.createQuery("SELECT equipo FROM Equipo equipo WHERE equipo.estado = 'ACTIVO'", Equipo.class).getResultList());
    }
}
