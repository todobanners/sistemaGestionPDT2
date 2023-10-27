package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.EquiposUbicacione;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface EquiposUbicacioneRemote {
    public void crearEquiposUbicacione(EquiposUbicacione equiposUbicacione);
    public List<EquiposUbicacione> obtenerEquiposUbicacione();

}
