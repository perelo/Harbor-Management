package cas2.controler.dao;

import cas2.model.Pontoon;

public interface DAOPontoon extends DAO<Pontoon, Integer> {

    public Iterable<Object[]> findNbTripsByBasinMonths(int nbMonths);
    
}
