package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.BajaEquipoDto;
import codigocreativo.uy.servidorapp.DTOMappers.BajaEquipoMapper;
import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
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
    private BajaEquipoMapper bajaEquipoMapper;


    @Override
    public void crearBajaEquipo(BajaEquipoDto bajaEquipo) {
        BajaEquipo bajaEquipoEntity = bajaEquipoMapper.toEntity(bajaEquipo);
        em.persist(bajaEquipoEntity);
        em.flush();
    }

    @Override
    public List<BajaEquipo> obtenerEquipos() {
        return em.createQuery("SELECT bajaEquipo FROM BajaEquipo bajaEquipo", BajaEquipo.class).getResultList();
    }
}