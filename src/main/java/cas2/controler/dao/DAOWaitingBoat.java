package cas2.controler.dao;

import java.util.List;

import cas2.controler.CExc;
import cas2.model.Model;
import cas2.model.WaitingBoat;

public interface DAOWaitingBoat extends DAO<WaitingBoat, Integer> {

    public List<WaitingBoat> findAll();
    public int getNextRank();
    public int computeNbDaysToWait(Model model);
    public void updateRank(WaitingBoat waitingBoat) throws CExc;
    
}
