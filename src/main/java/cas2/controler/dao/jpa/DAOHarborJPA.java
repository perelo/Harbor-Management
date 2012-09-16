package cas2.controler.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import cas2.controler.dao.DAOHarbor;
import cas2.model.Harbor;

public class DAOHarborJPA extends DAOGeneriqueJPA<Harbor, Integer>
                          implements DAOHarbor {

    public DAOHarborJPA(EntityManager entityManager) {
        super(entityManager);
    }
    
    @Override
    public List<Harbor> findAll() {
        return entityManager.createNamedQuery(
                Harbor.FIND_ALL, Harbor.class).
                    getResultList();
    }

}
