package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.PerfilesPermiso;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface PerfilesPermisoRemote {
    public void CrearPerfilesPermiso(PerfilesPermiso perfilesPermiso);
    public void modificarPerfilesPermiso(PerfilesPermiso perfilesPermiso);
    public List<PerfilesPermiso> obtenerPerfilesPermiso();
}
