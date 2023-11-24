package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.DTOMappers.UsuarioMapper;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class UsuarioBean implements UsuarioRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;

    @Inject
    UsuarioMapper usuarioMapper;

    @Override
    public void crearUsuario(UsuarioDto u) {
        Usuario usuario = usuarioMapper.toEntity(u);
        em.persist(usuario);
    }

    @Override
    public void modificarUsuario(UsuarioDto u) {
        Usuario usuario = usuarioMapper.toEntity(u);
        em.merge(usuario);
    }

    @Override
    public void eliminarUsuario(UsuarioDto u) {
        Usuario usuario = em.createQuery("UPDATE Usuario u SET u.estado = 'INACTIVO' WHERE u.id = :id", Usuario.class)
                .setParameter("id", u.getId())
                .getSingleResult();
        em.flush();
    }

    @Override
    public UsuarioDto obtenerUsuario(Long id) {
        return usuarioMapper.toDto(em.find(Usuario.class, id));
    }

    @Override
    public UsuarioDto obtenerUsuarioDto(Long id) {
        return usuarioMapper.toDto(em.find(Usuario.class, id));
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        return usuarioMapper.toDto(usuarios);
    }

    @Override
    public List<UsuarioDto> obtenerUsuariosFiltrado(String filtro, String valor) {
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u WHERE u." + filtro + " LIKE '%" + valor + "%'", Usuario.class).getResultList();
        return usuarioMapper.toDto(usuarios);
    }

    @Override
    public List<UsuarioDto> obtenerUsuariosPorEstado(Estados estado) {
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u WHERE u.estado = :estado", Usuario.class)
                .setParameter("estado", estado)
                .getResultList();
        return usuarioMapper.toDto(usuarios);
    }

    @Override
    public UsuarioDto login(String usuario, String password) {
        try {
            Usuario user = em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :usuario AND u.contrasenia = :password", Usuario.class)
                    .setParameter("usuario", usuario)
                    .setParameter("password", password)
                    .getSingleResult();

            if (user != null && user.getContrasenia().equals(password)) {
                return usuarioMapper.toDto(user);
            } else {
                return null;
            }
        } catch (NoResultException e) {
            System.out.println("No se encontro el usuario");
            return null;
        }
    }
}
