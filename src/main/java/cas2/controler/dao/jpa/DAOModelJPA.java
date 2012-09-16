package cas2.controler.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import cas2.controler.dao.*;
import cas2.model.Model;

public class DAOModelJPA extends DAOGeneriqueJPA<Model, Integer>
                         implements DAOModel {

    public DAOModelJPA(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Model> findAll() {
        return entityManager.createNamedQuery(
                Model.FIND_ALL, Model.class).
                    getResultList();
    }

}
