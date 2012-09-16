package cas2.controler.dao;

import java.util.List;

import cas2.model.Resident;

public interface DAOResident extends DAO<Resident, Integer> {

    public List<Resident> findMooredResidents();
    
}
