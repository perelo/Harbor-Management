package cas2.controler.dao;

public interface DAOFactory {
    
    public DAOBoat            getDAOBoat();
    public DAOHarbor          getDAOHarbor();
    public DAOModel           getDAOModel();
    public DAOOccupation      getDAOOccupation();
    public DAOOwner           getDAOOwner();
    public DAOParking         getDAOParking();
    public DAOResident        getDAOResident();
    public DAOTrip            getDAOTrip();
    public DAOEmplacement     getDAOEmplacement();
    public DAOEmplacementType getDAOEmplacementType();
    public DAOPassenger       getDAOPassenger();
    public DAOPontoon         getDAOPontoon();
    public DAOWaitingBoat     getDAOWaitingBoat();
    
}
