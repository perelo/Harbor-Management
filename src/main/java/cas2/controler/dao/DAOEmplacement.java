package cas2.controler.dao;

import cas2.model.Emplacement;
import cas2.model.Model;

public interface DAOEmplacement extends DAO<Emplacement, Integer> {

    public Iterable<Object[]> findEmptyByModel(Model model,
                                               int parkingDuration);
    
}
