package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.EquiposUbicacione;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class EquiposUbicacioneBean implements EquiposUbicacioneRemote {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void crearEquiposUbicacione(EquiposUbicacione equiposUbicacione) {
        em.persist(equiposUbicacione);
        em.flush();
    }

    @Override
    public List<EquiposUbicacione> obtenerEquiposUbicacione() {
        return em.createQuery("SELECT EquiposUbicacione FROM EquiposUbicacione equiposUbicacione", EquiposUbicacione.class).getResultList();
    }
}
