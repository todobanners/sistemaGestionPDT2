package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import jakarta.ejb.Remote;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

@Remote
public interface PerfilRemote {
    public void crearPerfil(Perfil p);
    public void modificarPerfil(Perfil p);
    public void eliminarPerfil(Perfil p);
    public Perfil obtenerPerfil(Long id);
    public List<Perfil> obtenerPerfiles();

}
