package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.EquiposUbicacioneDto;
import codigocreativo.uy.servidorapp.DTOMappers.EquiposUbicacioneMapper;
import codigocreativo.uy.servidorapp.entidades.EquiposUbicacione;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class EquiposUbicacioneBean implements EquiposUbicacioneRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Inject
    EquiposUbicacioneMapper equiposUbicacioneMapper;

    @Override
    public void crearEquiposUbicacione(EquiposUbicacioneDto equiposUbicacione) {
        em.persist(equiposUbicacioneMapper.toEntity(equiposUbicacione));
        em.flush();
    }

    @Override
    public List<EquiposUbicacioneDto> obtenerEquiposUbicacione() {
        return equiposUbicacioneMapper.toDto(em.createQuery("SELECT equiposUbicacione FROM EquiposUbicacione equiposUbicacione", EquiposUbicacione.class).getResultList());
    }
}
