package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

@Stateless
public class TipoIntervencioneBean implements TipoIntervencioneRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public List<TiposIntervencioneDto> obtenerTiposIntervenciones() {
        return em.createQuery("SELECT new codigocreativo.uy.servidorapp.DTO.TiposIntervencioneDto(t.id, t.nombreTipo, t.estado) FROM TiposIntervencione t", TiposIntervencioneDto.class).getResultList();
    }

    @Override
    public TiposIntervencioneDto obtenerTipoIntervencion(Long id) {
        TiposIntervencione tipoIntervencion = em.find(TiposIntervencione.class, id);
        return new TiposIntervencioneDto(tipoIntervencion.getId(), tipoIntervencion.getNombreTipo(), tipoIntervencion.getEstado());
    }

    @Override
    public boolean crearTipoIntervencion(TiposIntervencioneDto t) {
        TiposIntervencione tipoIntervencion = new TiposIntervencione();
        tipoIntervencion.setNombreTipo(t.getNombreTipo());
        tipoIntervencion.setEstado(t.getEstado());
        try {
            em.persist(tipoIntervencion);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

    @Override
    public boolean modificarTipoIntervencion(TiposIntervencioneDto tipoIntervencion) {
        TiposIntervencione tipoIntervencionMod = em.find(TiposIntervencione.class, tipoIntervencion.getId());

        tipoIntervencionMod.setEstado(tipoIntervencion.getEstado());
        try {
            em.merge(tipoIntervencionMod);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

    @Override
    public boolean eliminarTipoIntervencion(Long id) {
        try {
            //Change estado to inactivo
            em.createQuery("UPDATE TiposIntervencione t SET t.estado = codigocreativo.uy.servidorapp.enumerados.Estados.INACTIVO WHERE t.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }
}
