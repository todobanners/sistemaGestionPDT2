package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class EquipoBean implements EquipoRemote {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void crearEquipo(Equipo equipo) {
        em.persist(equipo);
        em.flush();
    }

    @Override
    public void modificarEquipo(Equipo equipo) {
        em.merge(equipo);
        em.flush();
    }

    @Override
    public void eliminarEquipo(Equipo equipo) {
        em.createQuery("UPDATE Equipo equipo SET equipo.estado = 'baja' WHERE equipo.id = :id")
                .setParameter("id", equipo.getId())
                .executeUpdate();
    }

    @Override
    public void obtenerEquipo(Long id) {
        em.find(Equipo.class, id);
    }


    @Override
    public List<Equipo> obtenerEquipos() {
        return em.createQuery("SELECT equipo FROM Equipo equipo WHERE equipo.estado = 'alta'", Equipo.class).getResultList();
    }
}
