package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.DefaultEntidad;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface DefaultRemote {
    void create(DefaultEntidad d) throws ServiciosException;

    //Listar los defaults
    List<DefaultEntidad> list() throws ServiciosException;
}
