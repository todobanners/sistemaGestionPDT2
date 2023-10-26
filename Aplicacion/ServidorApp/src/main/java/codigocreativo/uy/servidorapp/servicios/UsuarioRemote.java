package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UsuarioRemote {
    public void crearUsuario(Usuario u);
    public void modificarUsuario(Usuario u);
    public void eliminarUsuario(Usuario u);
    public void obtenerUsuario(Long id);
    public List<Usuario> obtenerUsuarios();

}
