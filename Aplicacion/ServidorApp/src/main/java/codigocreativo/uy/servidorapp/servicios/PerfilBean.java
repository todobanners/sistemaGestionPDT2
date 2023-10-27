package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class PerfilBean implements PerfilRemote {
@PersistenceContext
    private EntityManager em;

    @Override
    public void crearPerfil(Perfil p) {
        em.persist(p);
        em.flush();
    }

    @Override
    public void modificarPerfil(Perfil p) {
        em.merge(p);
        em.flush();
    }

    @Override
    public void eliminarPerfil(Perfil p) {
        em.createQuery("UPDATE Perfil p SET p.estado = 'baja' WHERE p.id = :id")
                .setParameter("id", p.getId())
                .executeUpdate();
    }

    @Override
    public void obtenerPerfil(Long id) {
        em.find(Perfil.class, id);
    }

    @Override
    public List<Perfil> obtenerPerfiles() {
        return em.createQuery("SELECT p FROM Perfil p WHERE p.estado = 'alta'", Perfil.class).getResultList();
    }
}
