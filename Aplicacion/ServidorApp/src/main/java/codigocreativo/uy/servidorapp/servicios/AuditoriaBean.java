package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Auditoria;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class AuditoriaBean implements AuditoriaRemote{

    @PersistenceContext
    private EntityManager em;
    @Override
    public void crearRegistro(Auditoria a){
        em.persist(a);
        em.flush();
    }
}
