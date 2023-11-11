package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.TiposEquipo;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TiposEquipoRemote {
    public void crearTiposEquipo(TiposEquipo tiposEquipo);
    public void modificarTiposEquipo(TiposEquipo tiposEquipo);
    public void obtenerTiposEquipo(Long id);
    public List<TiposEquipo> listarTiposEquipo();
}
