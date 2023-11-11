package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class EquipoBean implements EquipoRemote {
    @PersistenceContext (unitName = "default")
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
    public void eliminarEquipo(BajaEquipo bajaEquipo) {
        em.persist(bajaEquipo);
        em.createQuery("UPDATE Equipo e SET e.estado = 'baja' WHERE e.id = :id")
                .setParameter("id", bajaEquipo.getIdEquipo())
                .executeUpdate();
        em.flush();
    }

    @Override
    public void obtenerEquipo(Long id) {
        em.find(Equipo.class, id);
    }


    @Override
    public List<Equipo> listarEquipos() {
        return em.createQuery("SELECT equipo FROM Equipo equipo WHERE equipo.estado = 'ACTIVO'", Equipo.class).getResultList();
    }
}
