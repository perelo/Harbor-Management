package cas2.controler.dao.jpa;

import javax.persistence.EntityManager;

import cas2.controler.dao.DAOOwner;
import cas2.model.Owner;

public class DAOOwnerJPA extends DAOGeneriqueJPA<Owner, Integer>
                         implements DAOOwner {

    public DAOOwnerJPA(EntityManager entityManager) {
        super(entityManager);
    }

}
