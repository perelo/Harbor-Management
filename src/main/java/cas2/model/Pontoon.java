package cas2.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@NamedQueries ({
    @NamedQuery(name=Pontoon.NB_TRIPS_BY_BASIN_MONTHS,
                query="SELECT p.basin, COUNT(t) c " +
                      "FROM Occupation o join o.emplacement e " +
                      "                  join e.pontoon p, Trip t " +
                      "WHERE o.resident = t.boat AND " +
                      "t.departureDate > FUNC('ADD_MONTHS', CURRENT_DATE," +
                      "                                     :nbMonths) " +
                      "GROUP BY p.basin " +
                      "ORDER BY c DESC")
})
public class Pontoon implements Serializable, UserEditable {
    private static final long serialVersionUID = -1653018720659653633L;
    public static final String NB_TRIPS_BY_BASIN_MONTHS =
                                                "nbTripsByBasinMonths";

    public enum Cardinal { NORTH, SOUTH, EAST, WEST };
    
    @Id
    @GeneratedValue
    @Column (name="NUM_PONTOON")
    private int numPontoon;
    private Cardinal basin;
    private char pontoon;
    @Column (name="IS_RESERVED")
    private boolean isReserved; // reserve aux passagers
    
    public Pontoon () {}

    @Override
    public String toString() {
        return "Pontoon [numPontoon=" + numPontoon + ", basin=" + basin
                + ", pontoon=" + pontoon + ", isReserved=" + isReserved + "]";
    }

    public Pontoon(Cardinal basin, char pontoon, boolean isReserved) {
        super();
        // un trigger verifie qu'il n'existe pas deja un PONTOON
        // avec ce basin et ce pontoon
        this.basin = basin;
        this.pontoon = pontoon;
        this.isReserved = isReserved;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numPontoon;
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
        Pontoon other = (Pontoon) obj;
        if (numPontoon != other.numPontoon)
            return false;
        return true;
    }

    public Cardinal getBasin() {
        return basin;
    }

    public void setBasin(Cardinal basin) {
        this.basin = basin;
    }

    public char getPontoon() {
        return pontoon;
    }

    public void setPontoon(char pontoon) {
        this.pontoon = pontoon;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public int getNumPontoon() {
        return numPontoon;
    }

    @Override
    public String toStringForUsers() {
        return basin + " " + pontoon;
    }
    
}
