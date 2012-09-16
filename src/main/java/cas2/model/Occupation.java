package cas2.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

@Entity
public class Occupation implements Serializable, UserEditable {
    private static final long serialVersionUID = 8797524497733980969L;

    private static Map<String, Integer> occupationDurations;
    
    @Id
    @ManyToOne
    @JoinColumn(name="NUM_RES")
    private Resident resident;
    
    @Id
    @ManyToOne
    @JoinColumn(name="NUM_EMP")
    private Emplacement emplacement;

    @Id
    @Column (name="START_OCC_DATE")
    private Date startOccupationDate;

    @Column (name="OCC_DURATION")
    private int  occupationDuration; // en mois
    
    static {
        occupationDurations = new HashMap<String, Integer>();
        occupationDurations.put("Trimester", 3);
        occupationDurations.put("Semester", 6);
        occupationDurations.put("1 year", 12);
        occupationDurations.put("5 years", 60);
        occupationDurations.put("10 years", 120);
        occupationDurations.put("20 years", 250);
    }
    
    public Occupation() {}

    public Occupation(Resident resident, Emplacement emplacement,
            Date startOccupationDate, int occupationDuration) {
        // un trigger vérifie l'unicité du triplet
        // resident - emplacement - date_début_occupation
        super();
        this.resident = resident;
        this.emplacement = emplacement;
        this.startOccupationDate = startOccupationDate;
        this.occupationDuration = occupationDuration;
    }

    @Override
    public String toString() {
        return "Occupation [resident=" + resident + ", emplacement="
                + emplacement + ", startOccupationDate=" + startOccupationDate
                + ", occupationDuration=" + occupationDuration + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((emplacement == null) ? 0 : emplacement.hashCode());
        result = prime * result + occupationDuration;
        result = prime
                * result
                + ((startOccupationDate == null) ? 0 : startOccupationDate
                        .hashCode());
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
        Occupation other = (Occupation) obj;
        if (emplacement == null) {
            if (other.emplacement != null)
                return false;
        } else if (!emplacement.equals(other.emplacement))
            return false;
        if (occupationDuration != other.occupationDuration)
            return false;
        if (startOccupationDate == null) {
            if (other.startOccupationDate != null)
                return false;
        } else if (!startOccupationDate.equals(other.startOccupationDate))
            return false;
        return true;
    }

    public static Map<String, Integer> getOccupationDurations() {
        return occupationDurations;
    }

    public static void setOccupationDurations(
            Map<String, Integer> occupationDurations) {
        Occupation.occupationDurations = occupationDurations;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public Date getStartOccupationDate() {
        return startOccupationDate;
    }

    public void setStartOccupationDate(Date startOccupationDate) {
        this.startOccupationDate = startOccupationDate;
    }

    public int getOccupationDuration() {
        return occupationDuration;
    }

    public void setOccupationDuration(int occupationDuration) {
        this.occupationDuration = occupationDuration;
    }

    @Override
    public String toStringForUsers() {
        return resident.getNameBoat() + " - " + emplacement.getNumEmp()
                + " (" + startOccupationDate + ")";
    }

}
