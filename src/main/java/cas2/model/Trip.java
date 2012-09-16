package cas2.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name=Trip.FIND_CURRENT_TRIPS,
                query="SELECT t FROM Trip t WHERE t.realReturnDate IS NULL"),
    @NamedQuery(name=Trip.COMPUTE_NB_TRIPS_BY_YEAR_BOAT,
                query="SELECT b, FUNC('TO_CHAR', t.departureDate, 'YYYY'), count(t) " +
                      "FROM Trip t join t.boat b " +
                      "WHERE t.boat = b " +
                      "GROUP BY b, FUNC('TO_CHAR', t.departureDate, 'YYYY') " +
                      "ORDER BY b.nameBoat, FUNC('TO_CHAR', t.departureDate, 'YYYY')")
})
public class Trip implements Serializable, UserEditable { // Sortie
    private static final long serialVersionUID = -1620868223660733636L;
    public static final String FIND_CURRENT_TRIPS = "findCurrentTrips";
    public static final String COMPUTE_NB_TRIPS_BY_YEAR_BOAT =
                                            "computeNbTripsByYearBoat";
    
    @Id
    @GeneratedValue
    @Column (name="NUM_TRIP")
    private int numTrip;

    @ManyToOne
    @JoinColumn (name="NUM_BOAT", nullable=false)
    private Boat boat; // resident
    @Column (name="DEPARTURE_DATE", nullable=false)
    private Date departureDate;
    @Column (name="PLANNED_RETURN_DATE")
    private Date plannedReturnDate;
    @Column (name="REAL_RETURN_DATE")
    private Date realReturnDate;
    
    public Trip() {}

    public Trip(Boat boat, Date departureDate, Date plannedReturnDate,
            Date realReturnDate) {
        super();
        // un trigger verifie que le boat est bien un resident (isResident)
        // et que il n'existe pas deja un TRIP
        // pour ce bateau et cette date de depart
        this.boat = boat;
        this.departureDate = departureDate;
        this.plannedReturnDate = plannedReturnDate;
        this.realReturnDate = realReturnDate;
    }

    @Override
    public String toString() {
        return "Trip [numTrip=" + numTrip + ", boat=" + boat
                + ", departureDate=" + departureDate
                + (plannedReturnDate != null ? ", plannedReturnDate=" +
                                                plannedReturnDate : "")
                + (realReturnDate != null ? ", realReturnDate=" +
                                                realReturnDate : "")
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numTrip;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Trip other = (Trip) obj;
        if (numTrip != other.numTrip)
            return false;
        return true;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getPlannedReturnDate() {
        return plannedReturnDate;
    }

    public void setPlannedReturnDate(Date plannedReturnDate) {
        this.plannedReturnDate = plannedReturnDate;
    }

    public Date getRealReturnDate() {
        return realReturnDate;
    }

    public void setRealReturnDate(Date realReturnDate) {
        this.realReturnDate = realReturnDate;
    }

    public int getNumTrip() {
        return numTrip;
    }

    @Override
    public String toStringForUsers() {
        if (null == boat) // non persistant, pas une vrai sortie
            return " ";
        else
            return boat.getNameBoat() + " (" + departureDate + ")";
    }
    
}
