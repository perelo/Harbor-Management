package cas2.controler.dao;

import java.util.List;

import cas2.model.Trip;

public interface DAOTrip extends DAO<Trip, Integer> {

    List<Trip> findCurrentTrips();
    Iterable<Object[]> computeNbTripsByYearBoat();

}
