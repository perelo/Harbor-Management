package cas2.controler.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import cas2.controler.dao.DAOResident;
import cas2.model.Resident;

public class DAOResidentJPA extends DAOGeneriqueJPA<Resident, Integer>
                            implements DAOResident {

    public DAOResidentJPA(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Resident> findAll() {
        return entityManager.createNamedQuery(
                    Resident.FIND_ALL, Resident.class).
                        getResultList();
    }

    @Override
    public List<Resident> findMooredResidents() {
        return entityManager.createNamedQuery(
                    Resident.FIND_MOORED, Resident.class).
                        getResultList();
    }

}
