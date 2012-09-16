package cas2.controler.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import cas2.controler.dao.*;
import cas2.model.Boat;

public class DAOBoatJPA extends DAOGeneriqueJPA<Boat, Integer>
                        implements DAOBoat {

    public DAOBoatJPA(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Boat> findAll() {
        return entityManager.createNamedQuery(
                Boat.FIND_ALL, Boat.class).
                    getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<Object[]> computeNbBoatsByOwner() {
        return entityManager.createNamedQuery(
                Boat.COMPUTE_NB_BOATS_BY_OWNER).
                    getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<Object[]> computeNbBoatsByModel() {
        return entityManager.createNamedQuery(
                Boat.COMPUTE_NB_BOATS_BY_MODEL).
                    getResultList();
    }

}
