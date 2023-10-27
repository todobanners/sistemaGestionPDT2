package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class MarcasModeloBean implements MarcasModeloRemote{
    @PersistenceContext
    private EntityManager em;


    @Override
    public void crearMarcasModelo(MarcasModelo marcasModelo) {
        em.persist(marcasModelo);
        em.flush();
    }

    @Override
    public void modificarMarcasModelo(MarcasModelo marcasModelo) {
        em.merge(marcasModelo);
        em.flush();
    }

    @Override
    public void obtenerMarcasModelo(Long id) {
        em.find(MarcasModelo.class, id);
    }


    @Override
    public List<MarcasModelo> obtenerMarcasModelo() {
        return em.createQuery("SELECT MarcasModelo FROM MarcasModelo marcasModelo", MarcasModelo.class).getResultList();
    }
}

