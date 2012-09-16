package cas2.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Vector;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@DiscriminatorValue ("W")
@Table (name="WAITING_BOAT")
@NamedQueries({
    @NamedQuery(name=WaitingBoat.FIND_ALL,
                query="SELECT w FROM WaitingBoat w ORDER BY w.rank"),
    @NamedQuery(name=WaitingBoat.NEXT_RANK,
                query="SELECT FUNC('NVL', MAX(w.rank), 0) + 1 " +
                      "FROM WaitingBoat w"),
    @NamedQuery(name=WaitingBoat.UPDATE_RANK,
                query="UPDATE WaitingBoat w " +
                      "SET w.rank = w.rank - 1 " +
                      "WHERE w.rank > :rank")
})
@NamedNativeQueries({
    @NamedNativeQuery(name=WaitingBoat.DAYS_DO_WAIT,
                      query="SELECT WAITING_DELAY (?,?) FROM DUAL")
})
public class WaitingBoat extends Boat implements Serializable {
    private static final long serialVersionUID = 6878477202585340404L;
    public static final String FIND_ALL = "findAllWaitingBoats";
    public static final String NEXT_RANK = "getMaxRank";
    public static final String DAYS_DO_WAIT = "computeDaysToWait";
    public static final String UPDATE_RANK = "updateWaitingBoatsRank";
    
    @Column (name="DEMAND_DATE")
    private Date demandDate;
    private int rank;
    @Column (name="PLANNED_OCCUPATION_DURATION")
    private int plannedOccupationDuration; // en mois
    
    public WaitingBoat() { super(); }
    
    public WaitingBoat(String nameBoat, int serialNum, String insurance,
                       Owner owner, Harbor harbor, Model model,
                       Date demandDate, int rank,
                       int plannedOccupationDuration) {
        // un trigger verifie qu'on ajoute pas un WaitingBoat
        // si on en a deja 20
        super(nameBoat, serialNum, insurance, owner, harbor, model);
        this.demandDate = demandDate;
        this.rank = rank;
        this.plannedOccupationDuration = plannedOccupationDuration;
    }
    
    public WaitingBoat(Boat boat, Date demandDate, int rank,
                       int plannedOccupationDuration) {
        super(boat);
        this.demandDate = demandDate;
        this.rank = rank;
        this.plannedOccupationDuration = plannedOccupationDuration;
    }
    
    public static Vector<Vector<String>> getRowDataWaitingList(
                                            Vector<Object[]> vWaitingList) {
        
        Vector<Vector<String>> rowData = new Vector<Vector<String>>();
        
        for (Object[] wbAndDelay : vWaitingList) {
            
        	int delay = ((Number) wbAndDelay[1]).intValue();
        	
            Vector<String> oneRow = new Vector<String>();
            oneRow.add((((WaitingBoat) wbAndDelay[0])).toStringForUsers());
            oneRow.add(""+(((WaitingBoat) wbAndDelay[0])).
                                getPlannedOccupationDuration() + " months");
            oneRow.add(""+(((WaitingBoat) wbAndDelay[0])).getRank());
            oneRow.add(""+(delay > 0 ? delay : 0) + " days");
            
            rowData.add(oneRow);
        }

        return rowData;
        
    }

    @Override
    public String toString() {
        return "WaitingBoat [numBoat=" + numBoat + ", nameBoat=" + nameBoat
                + ", serialNum=" + serialNum + ", insurance=" + insurance
                + ", owner=" + owner + ", harbor=" + harbor + ", model="
                + model + ", demandDate=" + demandDate + ", rank=" + rank
                + ", plannedOccupationDuration=" + plannedOccupationDuration
                + "]";
    }

    public Date getDemandDate() {
        return demandDate;
    }

    public void setDemandDate(Date demandDate) {
        this.demandDate = demandDate;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPlannedOccupationDuration() {
        return plannedOccupationDuration;
    }

    public void setPlannedOccupationDuration(int plannedOccupationDuration) {
        this.plannedOccupationDuration = plannedOccupationDuration;
    }
    
}
