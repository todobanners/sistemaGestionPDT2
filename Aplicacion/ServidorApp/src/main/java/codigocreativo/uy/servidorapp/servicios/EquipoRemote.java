package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface EquipoRemote {
    public void crearEquipo(Equipo equipo);
    public void modificarEquipo(Equipo equipo);
    public void eliminarEquipo(Equipo equipo);
    public void obtenerEquipo(Long id);
    public List<Equipo> obtenerEquipos();

}