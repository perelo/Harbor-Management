package cas2.controler.dao.jpa;

import javax.persistence.EntityManager;

import cas2.controler.dao.*;
import cas2.model.Passenger;

public class DAOPassengerJPA extends DAOGeneriqueJPA<Passenger, Integer>
                          implements DAOPassenger {

    public DAOPassengerJPA(EntityManager entityManager) {
        super(entityManager);
    }

}
