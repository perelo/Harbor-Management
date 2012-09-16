package cas2.controler.dao.jpa;

import javax.persistence.EntityManager;

import cas2.controler.dao.DAOOccupation;
import cas2.model.Occupation;

public class DAOOccupationJPA extends DAOGeneriqueJPA<Occupation, Integer>
                          implements DAOOccupation {

    public DAOOccupationJPA(EntityManager entityManager) {
        super(entityManager);
    }

}