package cas2.controler.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import cas2.controler.dao.DAOEmplacement;
import cas2.model.Emplacement;
import cas2.model.Model;
import cas2.model.Parking;

public class DAOEmplacementJPA extends DAOGeneriqueJPA<Emplacement, Integer>
                               implements DAOEmplacement {

    public DAOEmplacementJPA(EntityManager entityManager) {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public Iterable<Object[]> findEmptyByModel(Model model,
                                               int parkingDuration) {
        
        boolean isResident = (parkingDuration == Parking.NO_DURATION ?
                                                            true : false);

        Query q = entityManager.createNamedQuery(
                    Emplacement.FIND_EMPTY_BY_MODEL);
        
        q.setParameter("length",     model.getLength());
        q.setParameter("width",      model.getWidth());
        q.setParameter("isReserved", !isResident);
        q.setParameter("parkingDuration", parkingDuration);
        
        return q.getResultList();
    }

}
