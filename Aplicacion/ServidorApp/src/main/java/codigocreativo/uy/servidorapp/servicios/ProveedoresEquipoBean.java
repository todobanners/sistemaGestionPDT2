package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Pais;
import codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProveedoresEquipoBean implements ProveedoresEquipoRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;

    @Override
    public void CrearProveedoresEquipo(ProveedoresEquipo proveedoresEquipo) {
        em.persist(proveedoresEquipo);
        em.flush();
    }

    @Override
    public void modificarProveedoresEquipo(ProveedoresEquipo proveedoresEquipo) {
        em.merge(proveedoresEquipo);
        em.flush();
    }

    @Override
    public void obtenerProveedoresEquipo(Long id) {
        em.find(ProveedoresEquipo.class, id);
    }


    @Override
    public List<ProveedoresEquipo> obtenerProveedoresEquipo() {
        return em.createQuery("SELECT ProveedoresEquipo FROM ProveedoresEquipo proveedoresEquipo", ProveedoresEquipo.class).getResultList();
    }
}

