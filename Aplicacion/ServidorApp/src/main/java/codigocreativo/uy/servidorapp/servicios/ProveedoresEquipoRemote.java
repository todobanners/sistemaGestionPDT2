package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ProveedoresEquipoRemote {

    public void CrearProveedoresEquipo(ProveedoresEquipo proveedoresEquipo);
    public void modificarProveedoresEquipo(ProveedoresEquipo proveedoresEquipo);
    public void obtenerProveedoresEquipo(Long id);
    public List<ProveedoresEquipo> obtenerProveedoresEquipo();
}
