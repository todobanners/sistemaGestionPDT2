package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Equipos;
import codigocreativo.uy.servidorapp.entidades.Ubicaciones;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

@Remote
public interface UbicacionRemote {
    //Se crea la primer implementacion Registro de ubicaciones
    void crearUbicacion(Ubicaciones ubi) throws ServiciosException;

    void modificarUbicacion(Ubicaciones ubi) throws ServiciosException;

    void modificarUbicacionPorId(Long id) throws ServiciosException;

    void modificarUbicacionPorNombre(String nombre) throws ServiciosException;

    void borrarUbicacion(Long id) throws ServiciosException;

    void moverEquipoDeUbicacion(Equipos equipo, Ubicaciones ubicacion) throws ServiciosException;
}
