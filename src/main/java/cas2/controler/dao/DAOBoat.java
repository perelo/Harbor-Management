package cas2.controler.dao;

import java.util.List;

import cas2.model.Boat;

public interface DAOBoat extends DAO<Boat, Integer> {

    public List<Boat> findAll();
    public Iterable<Object[]> computeNbBoatsByOwner();
    public Iterable<Object[]> computeNbBoatsByModel();
    
}
