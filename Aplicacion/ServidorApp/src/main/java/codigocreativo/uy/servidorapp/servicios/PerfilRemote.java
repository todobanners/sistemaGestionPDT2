package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface PerfilRemote {
    public void crearPerfil(Perfil p);

    public void modificarPerfil(Perfil p);
    public void eliminarPerfil(Perfil p);
    public void obtenerPerfil(Long id);
    public List<Perfil> obtenerPerfiles();
}
