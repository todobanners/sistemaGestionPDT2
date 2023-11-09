package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class BajaEquipoBean implements BajaEquipoRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;


    @Override
    public void crearBajaEquipo(BajaEquipo bajaEquipo) {
        em.persist(bajaEquipo);
        em.flush();
    }

    @Override
    public List<BajaEquipo> obtenerEquipos() {
        return em.createQuery("SELECT bajaEquipo FROM BajaEquipo bajaEquipo", BajaEquipo.class).getResultList();
    }
}