package cas2.controler.dao.jpa;

import javax.persistence.EntityManager;

import cas2.controler.dao.*;
import cas2.model.EmplacementType;

public class DAOEmplacementTypeJPA
                        extends DAOGeneriqueJPA<EmplacementType, Integer>
                        implements DAOEmplacementType {

    public DAOEmplacementTypeJPA(EntityManager entityManager) {
        super(entityManager);
    }

}
