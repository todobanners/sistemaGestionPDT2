package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import codigocreativo.uy.servidorapp.entidades.Pais;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class PaisBean implements PaisRemote{
    @PersistenceContext
    private EntityManager em;


    @Override
    public void crearPais(Pais pais) {
        em.persist(pais);
        em.flush();
    }

    @Override
    public void modificarPais(Pais pais) {
        em.merge(pais);
        em.flush();
    }

    @Override
    public void obtenerPais(Long id) {
        em.find(Pais.class, id);
    }


    @Override
    public List<Pais> obtenerpais() {
        return em.createQuery("SELECT Pais FROM Pais pais", Pais.class).getResultList();
    }
}
