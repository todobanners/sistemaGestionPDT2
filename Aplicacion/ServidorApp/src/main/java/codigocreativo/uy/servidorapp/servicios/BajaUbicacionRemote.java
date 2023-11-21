package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;
import jakarta.transaction.Transactional;

import java.util.List;

@Remote
public interface BajaUbicacionRemote {
    @Transactional
    void crearBajaUbicacion(BajaUbicacion bajaUbicacion) throws ServiciosException;

    void borrarUbicacion(Long id) throws ServiciosException;

    public List<BajaUbicacion> listarBajaUbicaciones() throws ServiciosException;

}
