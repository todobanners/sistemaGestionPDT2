package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import codigocreativo.uy.servidorapp.entidades.Pais;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface PaisRemote {
    public void crearPais(Pais pais);
    public void modificarPais(Pais pais);
    public void obtenerPais(Long id);
    public List<Pais> obtenerpais();
}
