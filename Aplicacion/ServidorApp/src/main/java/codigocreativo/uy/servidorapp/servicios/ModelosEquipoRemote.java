package codigocreativo.uy.servidorapp.servicios;
import codigocreativo.uy.servidorapp.DTO.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.entidades.ModelosEquipo;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface ModelosEquipoRemote {
    public void crearModelosEquipo(ModelosEquipoDto modelosEquipo);
/*    public void modificarModelosEquipo(ModelosEquipo modelosEquipo);
    public void obtenerModelosEquipo(Long id);*/
    public List<ModelosEquipoDto> listarModelosEquipo();
}
