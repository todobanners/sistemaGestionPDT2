package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.BajaEquipoDto;
import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class BajaEquipoBean implements BajaEquipoRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;


    @Override
    public void crearBajaEquipo(BajaEquipoDto bajaEquipo) {
        em.persist(bajaEquipo);
        em.flush();
    }

    @Override
    public List<BajaEquipo> obtenerEquipos() {
        return em.createQuery("SELECT bajaEquipo FROM BajaEquipo bajaEquipo", BajaEquipo.class).getResultList();
    }
}