package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class UsuarioBean implements UsuarioRemote {
    @PersistenceContext (unitName = "default")
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
        em.createQuery("UPDATE Usuario u SET u.estado = 'ELIMINADO' WHERE u.id = :id")
                .setParameter("id", u.getId())
                .executeUpdate();
        em.flush();
    }

    @Override
    public Usuario obtenerUsuario(Long id) {
        return em.find(Usuario.class, id);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    @Override
    public List<Usuario> obtenerUsuariosFiltrado(String filtro, String valor) {
        return em.createQuery("SELECT u FROM Usuario u WHERE u." + filtro + " LIKE '%" + valor + "%'", Usuario.class).getResultList();
    }

    @Override
    public Usuario login(String usuario, String password) {
        try {
            Usuario user = em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :usuario AND u.contrasenia = :password", Usuario.class)
                    .setParameter("usuario", usuario)
                    .setParameter("password", password)
                    .getSingleResult();

            if (user != null && user.getContrasenia().equals(password)) {
                return user;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            System.out.println("No se encontro el usuario");
            return null;
        }
    }
}
