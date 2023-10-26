package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Operacion;

public interface OperacionRemote {
    public void crearOperacion(Operacion o);
    public void modificarOperacion (Operacion o);
    
    public void eliminarOperacion (Operacion o);
    
}
