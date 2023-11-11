package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TipoIntervencioneRemote {
    public List<TiposIntervencione> obtenerTiposIntervenciones();
}
