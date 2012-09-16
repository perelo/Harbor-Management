package cas2.controler.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import cas2.controler.CExc;
import cas2.controler.ErrorMessage;
import cas2.controler.dao.DAOWaitingBoat;
import cas2.model.Model;
import cas2.model.WaitingBoat;

public class DAOWaitingBoatJPA extends DAOGeneriqueJPA<WaitingBoat, Integer>
                         implements DAOWaitingBoat {

    public DAOWaitingBoatJPA(EntityManager entityManager) {
        super(entityManager);
    }
    
    @Override
    public List<WaitingBoat> findAll() {
        return entityManager.createNamedQuery(
                WaitingBoat.FIND_ALL, WaitingBoat.class).
                    getResultList();
    }
    
    @Override
    public int getNextRank() {
        return ((Number) entityManager.createNamedQuery(
                WaitingBoat.NEXT_RANK).getResultList().get(0)).intValue();
    }

    @Override
    public int computeNbDaysToWait(Model model) {
        Query q = entityManager.createNamedQuery(
                    WaitingBoat.DAYS_DO_WAIT, Number.class);
        
        q.setParameter(1, model.getLength());
        q.setParameter(2, model.getWidth());
        
        return ((Number) q.getSingleResult()).intValue();
    }

    @Override
    public void updateRank(WaitingBoat waitingBoat) throws CExc {
        try {
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery(WaitingBoat.UPDATE_RANK).
                setParameter("rank", waitingBoat.getRank()).
                    executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new CExc("Waiting list", ErrorMessage.getSQLMessage(e));
        }
    }
}
