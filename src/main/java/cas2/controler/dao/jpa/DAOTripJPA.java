package cas2.controler.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.QueryHints;

import cas2.controler.dao.DAOTrip;
import cas2.model.Trip;

public class DAOTripJPA extends DAOGeneriqueJPA<Trip, Integer>
                        implements DAOTrip {

    public DAOTripJPA(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Trip> findCurrentTrips() {
        return entityManager.createNamedQuery(
                Trip.FIND_CURRENT_TRIPS, Trip.class).
                        getResultList();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Iterable<Object[]> computeNbTripsByYearBoat() {
        Query q = entityManager.createNamedQuery(
                Trip.COMPUTE_NB_TRIPS_BY_YEAR_BOAT, Object[].class);
        
        // Il faut cette instruction sinon, on a une erreur
        // "Not a GROUP BY expression"
        q.setHint(QueryHints.BIND_PARAMETERS, "False");
        
        return q.getResultList();
    }

}
