package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

@Stateless
public class PerfilBean implements PerfilRemote {
@PersistenceContext (unitName = "default")
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
        //retornar perfiles activos
        return em.createQuery("SELECT p FROM Perfil p", Perfil.class).getResultList();
    }

    @Override
    public List<Perfil> listarPerfilesPorNombre(String nombre) {
        //busca los perfiles por nombre
        return em.createQuery("SELECT p FROM Perfil p WHERE p.nombrePerfil LIKE :nombre", Perfil.class).setParameter("nombre", "%" + nombre + "%").getResultList();
    }

    @Override
    public List<Perfil> listarPerfilesPorEstado(Estados estado) {
        //busca los perfiles por estado
        return em.createQuery("SELECT p FROM Perfil p WHERE p.estado = :estado", Perfil.class).setParameter("estado", estado).getResultList();
    }


}
