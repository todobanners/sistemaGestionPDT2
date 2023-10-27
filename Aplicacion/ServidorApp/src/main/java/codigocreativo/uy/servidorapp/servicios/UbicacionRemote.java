package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Pais;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UbicacionRemote {
    public void crearUbicacion(Ubicacion ubicacion);
    public void modificarUbicacion(Ubicacion ubicacion);
    public void obtenerUbicacion(Long id);
    public void eliminarUbicacion(Ubicacion ubicacion);
    public List<Ubicacion> obtenerUbicacion();
}
