package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class TipoIntervencioneBean implements TipoIntervencioneRemote{
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public List<TiposIntervencione> obtenerTiposIntervenciones() {
        return em.createQuery("SELECT TiposIntervencione FROM TiposIntervencione tiposIntervencione", TiposIntervencione.class).getResultList();
    }
}
