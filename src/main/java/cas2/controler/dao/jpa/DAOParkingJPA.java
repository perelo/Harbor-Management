package cas2.controler.dao.jpa;

import javax.persistence.EntityManager;

import cas2.controler.dao.*;
import cas2.model.Parking;

public class DAOParkingJPA extends DAOGeneriqueJPA<Parking, Integer>
                          implements DAOParking {

    public DAOParkingJPA(EntityManager entityManager) {
        super(entityManager);
    }


}
