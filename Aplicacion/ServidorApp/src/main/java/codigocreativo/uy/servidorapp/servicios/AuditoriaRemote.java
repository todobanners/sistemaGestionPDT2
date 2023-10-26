package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Auditoria;
import jakarta.ejb.Remote;

@Remote
public interface AuditoriaRemote {
    public void crearRegistro (Auditoria a);
}
