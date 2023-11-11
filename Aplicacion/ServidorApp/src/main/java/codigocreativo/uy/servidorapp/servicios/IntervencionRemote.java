package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Intervencion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface IntervencionRemote {
    // CRUD para intervenciones
    public Intervencion crear(Intervencion intervencion) throws ServiciosException;
    public void actualizar(Intervencion intervencion) throws ServiciosException;
    public void eliminar(Long id) throws ServiciosException;
    public Intervencion obtenerPorId(Long id) throws ServiciosException;
    public List<Intervencion> obtenerTodas() throws ServiciosException;

}
