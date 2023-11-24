package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.BajaEquipoDto;
import codigocreativo.uy.servidorapp.DTOMappers.BajaEquipoMapper;
import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class BajaEquipoBean implements BajaEquipoRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;

    @Inject
    BajaEquipoMapper bajaEquipoMapper;


    /**
     * Crea una baja de equipo.
     * También toma el estado de la baja de equipo y la aplica al equipo (también puede reactivar un equipo).
     * @param bajaEquipo Recibe un objeto de tipo BajaEquipoDto
     */
    @Override
    public void crearBajaEquipo(BajaEquipoDto bajaEquipo) {
        em.persist(bajaEquipoMapper.toEntity(bajaEquipo));
        Estados estadoEn = bajaEquipo.getEstado();

        em.createQuery("UPDATE Equipo equipo SET equipo.estado = :estadoEnum WHERE equipo.id = :id")
                .setParameter("id", bajaEquipo.getIdEquipo().getId())
                .setParameter("estadoEnum", estadoEn)
                .executeUpdate();
    }

    @Override
    public List<BajaEquipoDto> obtenerBajasEquipos() {
        return bajaEquipoMapper.toDto(em.createQuery("SELECT bajaEquipo FROM BajaEquipo bajaEquipo", BajaEquipo.class).getResultList());
    }

    @Override
    public BajaEquipoDto obtenerBajaEquipo(Long id) {
        return bajaEquipoMapper.toDto(em.find(BajaEquipo.class, id));
    }
}