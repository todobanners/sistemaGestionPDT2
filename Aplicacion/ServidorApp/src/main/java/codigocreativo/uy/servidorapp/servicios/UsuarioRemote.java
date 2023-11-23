package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UsuarioRemote {
    public void crearUsuario(Usuario u);
    public void modificarUsuario(Usuario u);
    public void eliminarUsuario(Usuario u);
    public Usuario obtenerUsuario(Long id);
    public Usuario obtenerUsuarioPorCI(String ci);
    public List<Usuario> obtenerUsuarios();
    public List<Usuario> obtenerUsuariosFiltrado(String filtro, String valor);
    public Usuario login(String usuario, String password);
    public List<Usuario> obtenerUsuariosPorEstado(Estados estado);
}
