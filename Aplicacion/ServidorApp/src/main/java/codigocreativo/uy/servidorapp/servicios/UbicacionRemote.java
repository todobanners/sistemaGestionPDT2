package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

@Remote
public interface UbicacionRemote {
    //Se crea la primer implementacion Registro de ubicaciones
    void crearUbicacion(Ubicacion ubi) throws ServiciosException;

    void modificarUbicacion(Ubicacion ubi) throws ServiciosException;

    void modificarUbicacionPorId(Long id) throws ServiciosException;

    void modificarUbicacionPorNombre(String nombre) throws ServiciosException;

    void borrarUbicacion(Long id) throws ServiciosException;

    void moverEquipoDeUbicacion(Equipo equipo, Long ubicacion) throws ServiciosException;
}
