package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

@Stateless
public class PerfilBean implements PerfilRemote {
    @PersistenceContext(unitName = "default")
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
        //use the enum to set the state
        em.createQuery("UPDATE Perfil p SET p.estado = 'ELIMINADO' WHERE p.id = :id").setParameter("id", p.getId()).executeUpdate();
    }

    @Override
    public Perfil obtenerPerfil(Long id) {
        return em.find(Perfil.class, id);
    }

    @Override
    public List<Perfil> obtenerPerfiles() {
        return em.createQuery("SELECT p FROM Perfil p", Perfil.class).getResultList();
    }


}
