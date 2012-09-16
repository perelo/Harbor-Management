package cas2.model;

import java.io.Serializable;
import java.sql.Date;

public class ParkingId implements Serializable {
    private static final long serialVersionUID = 62256089791726570L;
    
    private int passenger;
    private int emplacement;
    private Date startParkingDate;

    public ParkingId() {}

    public ParkingId(int passenger, int emplacement, Date startParkingDate) {
        this.passenger = passenger;
        this.emplacement = emplacement;
        this.startParkingDate = startParkingDate;
    }

    @Override
    public String toString() {
        return "ParkingId [passenger=" + passenger + ", emplacement="
                + emplacement + ", startParkingDate=" + startParkingDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + emplacement;
        result = prime * result + passenger;
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
        ParkingId other = (ParkingId) obj;
        if (emplacement != other.emplacement)
            return false;
        if (passenger != other.passenger)
            return false;
        if (startParkingDate == null) {
            if (other.startParkingDate != null)
                return false;
        } else if (!startParkingDate.equals(other.startParkingDate))
            return false;
        return true;
    }

    public int getPassenger() {
        return passenger;
    }

    public void setPassenger(int passenger) {
        this.passenger = passenger;
    }

    public int getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(int emplacement) {
        this.emplacement = emplacement;
    }

    public Date getStartParkingDate() {
        return startParkingDate;
    }

    public void setStartParkingDate(Date startParkingDate) {
        this.startParkingDate = startParkingDate;
    }
    
}
