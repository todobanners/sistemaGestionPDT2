package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Auditoria;
import codigocreativo.uy.servidorapp.entidades.Intervencion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface AuditoriaRemote {
    public void crearRegistro (Auditoria a);
    public List<Auditoria> obtenerTodas() throws ServiciosException;
}
