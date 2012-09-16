package cas2.model;

import java.io.Serializable;
import java.sql.Date;

public class OccupationId implements Serializable {
    private static final long serialVersionUID = 3940682753580098653L;

    private int resident;
    private int emplacement;
    private Date startOccupationDate;
    
    public OccupationId() {}

    public OccupationId(int resident, int emplacement) {
        this.resident = resident;
        this.emplacement = emplacement;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + emplacement;
        result = prime * result + resident;
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
        OccupationId other = (OccupationId) obj;
        if (emplacement != other.emplacement)
            return false;
        if (resident != other.resident)
            return false;
        return true;
    }

    public int getResident() {
        return resident;
    }

    public void setResident(int resident) {
        this.resident = resident;
    }

    public int getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(int emplacement) {
        this.emplacement = emplacement;
    }

    public Date getStartOccupationDate() {
        return startOccupationDate;
    }

    public void setStartOccupationDate(Date startOccupationDate) {
        this.startOccupationDate = startOccupationDate;
    }
    
}
