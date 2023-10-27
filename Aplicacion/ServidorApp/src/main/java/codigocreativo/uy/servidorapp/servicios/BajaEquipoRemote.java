package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface BajaEquipoRemote {
    public void crearBajaEquipo(BajaEquipo bajaEquipoequipo);
    public List<BajaEquipo> obtenerEquipos();

}
