package cas2.model;

import java.io.Serializable;

import cas2.model.Harbor;

import javax.persistence.*;

@Entity
public class Passenger implements Serializable { // independant de Boat
    private static final long serialVersionUID = 7983908715398423973L;
    
    @Id
    @GeneratedValue
    @Column (name="NUM_PASSENGER")
    private int numPassenger;
    
    @ManyToOne
    @JoinColumn (name="ORIGIN", nullable=false)
    private Harbor origin;
    @ManyToOne
    @JoinColumn (name="DESTINATION", nullable=false)
    private Harbor destination;
    
    public Passenger () {}
    
    public Passenger(Harbor origin, Harbor destination) {
        super();
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Passenger [origin=" + origin + ", destination=" + destination
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numPassenger;
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
        Passenger other = (Passenger) obj;
        if (numPassenger != other.numPassenger)
            return false;
        return true;
    }

    public Harbor getOrigin() {
        return origin;
    }

    public void setOrigin(Harbor origin) {
        this.origin = origin;
    }

    public Harbor getDestination() {
        return destination;
    }

    public void setDestination(Harbor destination) {
        this.destination = destination;
    }

    public int getNumPassenger() {
        return numPassenger;
    }
    
}
