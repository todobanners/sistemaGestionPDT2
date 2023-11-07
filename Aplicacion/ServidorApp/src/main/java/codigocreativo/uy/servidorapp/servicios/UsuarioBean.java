package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class UsuarioBean implements UsuarioRemote {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void crearUsuario(Usuario u) {
        em.persist(u);
        em.flush();
    }

    @Override
    public void modificarUsuario(Usuario u) {
        em.merge(u);
        em.flush();
    }

    @Override
    public void eliminarUsuario(Usuario u) {
        em.createQuery("UPDATE Usuario u SET u.estado = 'baja' WHERE u.id = :id")
                .setParameter("id", u.getId())
                .executeUpdate();
    }

    @Override
    public void obtenerUsuario(Long id) {
        em.find(Usuario.class, id);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    @Override
    public List<Usuario> obtenerUsuariosFiltrado(String filtro, String valor) {
        return em.createQuery("SELECT u FROM Usuario u WHERE u." + filtro + " LIKE '%" + valor + "%'", Usuario.class).getResultList();
    }
}
