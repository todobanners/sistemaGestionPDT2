package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UsuarioRemote {
    public void crearUsuario(Usuario u);
    public void modificarUsuario(Usuario u);
    public void eliminarUsuario(Usuario u);
    public Usuario obtenerUsuario(Long id);
    public List<Usuario> obtenerUsuarios();
    public List<Usuario> obtenerUsuariosFiltrado(String filtro, String valor);
    public Usuario login(String usuario, String password);
}
