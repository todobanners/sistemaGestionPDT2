package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface BajaUbicacionRemote {
    //Se crea la primer implementacion Registro de ubicaciones
    void crearBajaUbicacion(Ubicacion ubi) throws ServiciosException;

    public List<BajaUbicacion> listarBajaUbicaciones() throws ServiciosException;

}
