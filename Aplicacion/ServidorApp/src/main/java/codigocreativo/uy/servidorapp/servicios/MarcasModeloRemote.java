package codigocreativo.uy.servidorapp.servicios;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface MarcasModeloRemote {
    public void crearMarcasModelo(MarcasModelo marcasModelo);
    public void modificarMarcasModelo(MarcasModelo marcasModelo);
    public void obtenerMarcasModelo(Long id);
    public List<MarcasModelo> obtenerMarcasModelo();
}
