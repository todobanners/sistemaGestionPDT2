package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Instituciones;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface InstitucionRemote {
    public void agregarInstitucion(Institucion i);
    public void eliminarInstitucion(Institucion i);
    public void modificarInstitucion(Institucion i);
    public List<Ubicacion> obtenerUbicaciones();
    public List<Institucion> obtenerInstituciones();
    public Instituciones obtenerInstitucionPorNombre(String nombre);
}
