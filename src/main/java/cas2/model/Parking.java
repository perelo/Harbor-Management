package cas2.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@IdClass(ParkingId.class)
public class Parking implements Serializable, UserEditable {
    private static final long serialVersionUID = -2581374840964554435L;
    public static final int NO_DURATION = -1;

    @Id
    @ManyToOne
    @JoinColumn(name="NUM_PASS")
    private Passenger passenger;
    
    @Id
    @ManyToOne
    @JoinColumn(name="NUM_EMP")
    private Emplacement emplacement;
    
    @Id
    @Column(name="START_PARKING_DATE")
    private Date startParkingDate;
    
    @Column(name="PARKING_DURATION", nullable=false)
    private int parkingDuration; // en jours
    
    public Parking() {}
    
    public Parking(Passenger passenger, Emplacement emplacement,
            Date startParkingDate, int parkingDuration) {
        this.passenger = passenger;
        this.emplacement = emplacement;
        this.startParkingDate = startParkingDate;
        this.parkingDuration = parkingDuration;
    }

    @Override
    public String toString() {
        return "Parking [passenger=" + passenger + ", emplacement="
                + emplacement + ", startParkingDate=" + startParkingDate
                + ", parkingDuration=" + parkingDuration + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((emplacement == null) ? 0 : emplacement.hashCode());
        result = prime * result
                + ((passenger == null) ? 0 : passenger.hashCode());
        result = prime
                * result
                + ((startParkingDate == null) ? 0 : startParkingDate.hashCode());
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
        Parking other = (Parking) obj;
        if (emplacement == null) {
            if (other.emplacement != null)
                return false;
        } else if (!emplacement.equals(other.emplacement))
            return false;
        if (passenger == null) {
            if (other.passenger != null)
                return false;
        } else if (!passenger.equals(other.passenger))
            return false;
        if (startParkingDate == null) {
            if (other.startParkingDate != null)
                return false;
        } else if (!startParkingDate.equals(other.startParkingDate))
            return false;
        return true;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public Date getStartParkingDate() {
        return startParkingDate;
    }

    public void setStartParkingDate(Date startParkingDate) {
        this.startParkingDate = startParkingDate;
    }

    public int getParkingDuration() {
        return parkingDuration;
    }

    public void setParkingDuration(int parkingDuration) {
        this.parkingDuration = parkingDuration;
    }

    @Override
    public String toStringForUsers() {
        return emplacement.toStringForUsers() + " - " + startParkingDate +
               " (" + parkingDuration + " days)";
    }
    
}
