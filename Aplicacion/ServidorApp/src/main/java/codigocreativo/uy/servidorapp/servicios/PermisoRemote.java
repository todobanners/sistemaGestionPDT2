package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Permiso;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface PermisoRemote {
    public void crearPermiso(Permiso p);
    public void modificarPermiso(Permiso p);
    public void eliminarPermiso(Permiso p);
    public void obtenerPermiso(Long id);
    public List<Permiso> obtenerPermisos();

}
