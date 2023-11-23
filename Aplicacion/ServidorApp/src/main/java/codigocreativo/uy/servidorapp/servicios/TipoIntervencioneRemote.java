package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TipoIntervencioneRemote {
    public List<TiposIntervencioneDto> obtenerTiposIntervenciones();
    public TiposIntervencioneDto obtenerTipoIntervencion(Long id);
    public boolean crearTipoIntervencion(TiposIntervencioneDto tipoIntervencion);
    public boolean modificarTipoIntervencion(TiposIntervencioneDto tipoIntervencion);
    public boolean eliminarTipoIntervencion(Long id);
}
