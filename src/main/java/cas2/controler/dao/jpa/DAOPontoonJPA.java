package cas2.controler.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import cas2.controler.dao.DAOPontoon;
import cas2.model.Pontoon;

public class DAOPontoonJPA extends DAOGeneriqueJPA<Pontoon, Integer>
                           implements DAOPontoon {

    public DAOPontoonJPA(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<Object[]> findNbTripsByBasinMonths(int nbMonths) {
        
        Query q = entityManager.createNamedQuery(
                            Pontoon.NB_TRIPS_BY_BASIN_MONTHS);
        
        q.setParameter("nbMonths", 0 - nbMonths);

        return q.getResultList();
    }


}
