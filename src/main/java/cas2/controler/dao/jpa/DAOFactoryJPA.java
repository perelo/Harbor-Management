package cas2.controler.dao.jpa;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import cas2.controler.dao.*;
import cas2.model.*;
import cas2.model.Pontoon.Cardinal;

public class DAOFactoryJPA implements DAOFactory {
    
    private static DAOFactoryJPA __instance;
    
    public static DAOFactoryJPA getInstance() {
        if (__instance == null) {
            __instance = new DAOFactoryJPA();
        }
        
        return __instance;
    }
    
    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("harborManagmentPU");
    
    private EntityManager entityManager;
    
    private DAOBoat            daoBoat; 
    private DAOHarbor          daoHarbor;
    private DAOModel           daoModel;
    private DAOOccupation      daoOccupation;
    private DAOOwner           daoOwner;
    private DAOParking         daoParking;
    private DAOResident        daoResident;
    private DAOTrip            daoTrip;
    private DAOEmplacement     daoEmplacement;
    private DAOEmplacementType daoEmpType;
    private DAOPassenger       daoPassenger;
    private DAOPontoon         daoPontoon;
    private DAOWaitingBoat     daoWaitingBoat;

    private DAOFactoryJPA() {
        // Initializes the Entity manager
        this.entityManager = entityManagerFactory.createEntityManager();
    }
    
    public void closeEm() {
        entityManager.close();
        entityManagerFactory.close();
    }


    public DAOBoat getDAOBoat() {
        if (null == daoBoat)
            daoBoat = new DAOBoatJPA(entityManager);
        
        return daoBoat;
    }
    
    @Override
    public DAOHarbor getDAOHarbor() {
        if (null == daoHarbor)
            daoHarbor = new DAOHarborJPA(entityManager);
        
        return daoHarbor;
    }
    
    @Override
    public DAOModel getDAOModel() {
        if (null == daoModel)
            daoModel = new DAOModelJPA(entityManager);
        
        return daoModel;
    }

    @Override
    public DAOOwner getDAOOwner() {
        if (null == daoOwner)
            daoOwner = new DAOOwnerJPA(entityManager);
        
        return daoOwner;
    }

    @Override
    public DAOOccupation getDAOOccupation() {
        if (null == daoOccupation)
            daoOccupation = new DAOOccupationJPA(entityManager);
        
        return daoOccupation;
    }

    @Override
    public DAOParking getDAOParking() {
        if (null == daoParking)
            daoParking = new DAOParkingJPA(entityManager);
        
        return daoParking;
    }
    
    @Override
    public DAOPassenger getDAOPassenger() {
        if (null == daoPassenger)
            daoPassenger = new DAOPassengerJPA(entityManager);
        
        return daoPassenger;
    }
    
    @Override
    public DAOResident getDAOResident() {
        if (null == daoResident)
            daoResident = new DAOResidentJPA(entityManager);
        
        return daoResident;
    }

    @Override
    public DAOTrip getDAOTrip() {
        if (null == daoTrip)
            daoTrip = new DAOTripJPA(entityManager);
        
        return daoTrip;
    }

    @Override
    public DAOEmplacement getDAOEmplacement() {
        if (null == daoEmplacement)
            daoEmplacement = new DAOEmplacementJPA(entityManager);
        
        return daoEmplacement;
    }

    @Override
    public DAOEmplacementType getDAOEmplacementType() {
        if (null == daoEmpType)
            daoEmpType = new DAOEmplacementTypeJPA(entityManager);
        
        return daoEmpType;
    }

    @Override
    public DAOPontoon getDAOPontoon() {
        if (null == daoPontoon)
            daoPontoon = new DAOPontoonJPA(entityManager);
        
        return daoPontoon;
    }
    
    @Override
    public DAOWaitingBoat getDAOWaitingBoat() {
        if (null == daoWaitingBoat)
            daoWaitingBoat = new DAOWaitingBoatJPA(entityManager);
        
        return daoWaitingBoat;
    }
    
    public void generateTuples() {
        
        EntityTransaction tr = entityManager.getTransaction();
        
        /* Ports */
        tr.begin();
        Harbor ma = new Harbor("MA", "Marseille");
        Harbor na = new Harbor("NA", "Nantes");
        Harbor lr = new Harbor("LR", "La Rochelle");
        Harbor aj = new Harbor("AJ", "Ajaccio");
        
        entityManager.persist(ma);
        entityManager.persist(na);
        entityManager.persist(lr);
        entityManager.persist(aj);
        tr.commit();
        /* */
        
        /* Proprietaires */
        tr.begin();
        Owner pierre  = new Owner(
                            "PIERRE", "01 01 01 01 01",
                            new Address("Ad Pierre", 3, 13001, "Marseille"));
        Owner paul    = new Owner(
                            "PAUL", "02 02 02 02 02",
                            new Address("Ad Paul", 3, 75001, "Paris"));
        Owner jacques = new Owner(
                            "JACQUES", "03 03 03 03 03",
                            new Address("Ad Jacques", 3, 69001, "Lyon"));
        
        entityManager.persist(pierre);
        entityManager.persist(paul);
        entityManager.persist(jacques);
        tr.commit();
        /* */
        
        /* type_emp */
        tr.begin();
        EmplacementType et1 = new EmplacementType(10, 5, 3);
        EmplacementType et2 = new EmplacementType(25, 10, 5);
        EmplacementType et3 = new EmplacementType(30, 13, 7);
        EmplacementType et4 = new EmplacementType(30, 15, 8);
        EmplacementType et5 = new EmplacementType(40, 16, 9);
        
        entityManager.persist(et1);
        entityManager.persist(et2);
        entityManager.persist(et3);
        entityManager.persist(et4);
        entityManager.persist(et5);
        tr.commit();
        /* */
        
        /* Pontons */
        tr.begin();
        Pontoon pon1 = new Pontoon(Cardinal.NORTH, 'A', true);
        Pontoon pon2 = new Pontoon(Cardinal.NORTH, 'B', true);
        Pontoon pon3 = new Pontoon(Cardinal.NORTH, 'C', false);
        Pontoon pon4 = new Pontoon(Cardinal.SOUTH, 'A', false);
        Pontoon pon5 = new Pontoon(Cardinal.SOUTH, 'B', false);
        Pontoon pon6 = new Pontoon(Cardinal.SOUTH, 'C', false);
        
        entityManager.persist(pon1);
        entityManager.persist(pon2);
        entityManager.persist(pon3);
        entityManager.persist(pon4);
        entityManager.persist(pon5);
        entityManager.persist(pon6);
        tr.commit();
        /* */
        
        /* Emplacements */
        tr.begin();
        Emplacement emp1  = new Emplacement(et5, pon1, 1);
        Emplacement emp2  = new Emplacement(et4, pon1, 2);
        Emplacement emp3  = new Emplacement(et3, pon2, 1);
        Emplacement emp4  = new Emplacement(et2, pon2, 2);
        Emplacement emp5  = new Emplacement(et2, pon3, 1);
        Emplacement emp6  = new Emplacement(et1, pon3, 2);
        Emplacement emp7  = new Emplacement(et1, pon4, 1);
        Emplacement emp8  = new Emplacement(et2, pon4, 2);
        Emplacement emp9  = new Emplacement(et3, pon5, 1);
        Emplacement emp10 = new Emplacement(et4, pon5, 2);
        Emplacement emp11 = new Emplacement(et4, pon6, 2);
        Emplacement emp12 = new Emplacement(et5, pon6, 2);
        
        entityManager.persist(emp1);
        entityManager.persist(emp2);
        entityManager.persist(emp3);
        entityManager.persist(emp4);
        entityManager.persist(emp5);
        entityManager.persist(emp6);
        entityManager.persist(emp7);
        entityManager.persist(emp8);
        entityManager.persist(emp9);
        entityManager.persist(emp10);
        entityManager.persist(emp11);
        entityManager.persist(emp12);
        tr.commit();
        /* */
        
        /* Modeles */
        tr.begin();
        Model mod1 = new Model("MOD1", 1, "C1", 10, 8, 3);
        Model mod2 = new Model("MOD1", 2, "C1", 28, 12, 6);
        Model mod3 = new Model("MOD3", 3, "C2", 26, 14, 6);
        Model mod4 = new Model("MOD4", 1, "C3", 38, 14, 8);
        Model mod5 = new Model("MOD3", 2, "C2", 8, 4, 2);
        
        entityManager.persist(mod1);
        entityManager.persist(mod2);
        entityManager.persist(mod3);
        entityManager.persist(mod4);
        entityManager.persist(mod5);
        tr.commit();
        /* */
        
        /* Bateaux résidents */
        tr.begin();
        Resident res1 = new Resident("BAT1", 0, null, pierre,  ma, mod4);
        Resident res2 = new Resident("BAT2", 0, null, pierre,  ma, mod5);
        Resident res3 = new Resident("BAT3", 0, null, paul,    lr, mod1);
        Resident res4 = new Resident("BAT4", 0, null, jacques, na, mod5);
        Resident res5 = new Resident("BAT5", 0, null, jacques, na, mod3);
        Resident res6 = new Resident("BAT6", 0, null, jacques, na, mod3);
        
        entityManager.persist(res1);
        entityManager.persist(res2);
        entityManager.persist(res3);
        entityManager.persist(res4);
        entityManager.persist(res5);
        entityManager.persist(res6);
        tr.commit();
        /* */
        
        /* Occupations */
        tr.begin();
        Occupation occ1 =
                new Occupation(res1, emp12, Date.valueOf("2008-01-01"), 60);
        Occupation occ2 =
                new Occupation(res2,  emp6, Date.valueOf("2009-02-01"),  4);
        Occupation occ3 =
                new Occupation(res3,  emp5, Date.valueOf("2009-03-04"), 12);
        Occupation occ4 =
                new Occupation(res4,  emp7, Date.valueOf("2008-12-20"),  6);
        Occupation occ5 =
                new Occupation(res5, emp10, Date.valueOf("2008-08-01"), 12);
        Occupation occ6 =
                new Occupation(res6, emp11, Date.valueOf("2005-01-01"), 60);
        
        entityManager.persist(occ1);
        entityManager.persist(occ2);
        entityManager.persist(occ3);
        entityManager.persist(occ4);
        entityManager.persist(occ5);
        entityManager.persist(occ6);
        tr.commit();
        /* */
        
        /* Bateaux passagers * /
        tr.begin();
        Passenger pas1  = new Passenger(ma, na);
        Passenger pas2  = new Passenger(ma, lr);
        Passenger pas3  = new Passenger(ma, aj);
        Passenger pas4  = new Passenger(na, ma);
        Passenger pas5  = new Passenger(na, lr);
        Passenger pas6  = new Passenger(na, aj);
        Passenger pas7  = new Passenger(lr, ma);
        Passenger pas8  = new Passenger(lr, na);
        Passenger pas9  = new Passenger(lr, aj);
        Passenger pas10 = new Passenger(aj, ma);
        Passenger pas11 = new Passenger(aj, na);
        Passenger pas12 = new Passenger(aj, lr);
        Passenger pas13 = new Passenger(ma, na);
        Passenger pas14 = new Passenger(na, lr);
        Passenger pas15 = new Passenger(lr, aj);
        Passenger pas16 = new Passenger(aj, na);
        
        entityManager.persist(pas1);
        entityManager.persist(pas2);
        entityManager.persist(pas3);
        entityManager.persist(pas4);
        entityManager.persist(pas5);
        entityManager.persist(pas6);
        entityManager.persist(pas7);
        entityManager.persist(pas8);
        entityManager.persist(pas9);
        entityManager.persist(pas10);
        entityManager.persist(pas11);
        entityManager.persist(pas12);
        entityManager.persist(pas13);
        entityManager.persist(pas14);
        entityManager.persist(pas15);
        entityManager.persist(pas16);
        tr.commit();
        /* */
        
        /* Parking */
        tr.begin();
        // aucun
        tr.commit();
        /* */
        
        /* Liste d'attente */
        tr.begin();
        // aucun
        tr.commit();
        /* */
        
        /* Sorties */
        tr.begin();
        Trip trip1 = new Trip(res1, Date.valueOf("2008-01-20"),
                                    Date.valueOf("2008-01-25"),
                                    Date.valueOf("2008-01-24"));
        Trip trip2 = new Trip(res1, Date.valueOf("2008-03-24"),
                                    Date.valueOf("2008-03-27"),
                                    Date.valueOf("2008-03-29"));
        Trip trip3 = new Trip(res1, Date.valueOf("2008-06-10"),
                                    Date.valueOf("2008-06-15"),
                                    Date.valueOf("2008-06-15"));
        Trip trip4 = new Trip(res1, Date.valueOf("2008-08-07"),
                                    Date.valueOf("2008-08-20"),
                                    Date.valueOf("2008-08-19"));
        Trip trip5 = new Trip(res2, Date.valueOf("2009-02-07"),
                                    Date.valueOf("2009-02-12"),
                                    Date.valueOf("2009-02-12"));
        Trip trip6 = new Trip(res2, Date.valueOf("2009-03-03"),
                                    Date.valueOf("2009-03-05"),
                                    Date.valueOf("2009-03-05"));
        
        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.persist(trip3);
        entityManager.persist(trip4);
        entityManager.persist(trip5);
        entityManager.persist(trip6);
        tr.commit();
        /* */

    }

}
