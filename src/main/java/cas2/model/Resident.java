package cas2.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@DiscriminatorValue ("R")
@NamedQueries({
    @NamedQuery(name=Resident.FIND_ALL,
                query="SELECT r FROM Resident r ORDER BY r.nameBoat"),
    @NamedQuery(name=Resident.FIND_MOORED,
    query="SELECT r FROM Resident r " +
          "WHERE r.numBoat NOT IN " +
          "                (SELECT b.numBoat FROM Trip t join t.boat b " +
          "                 WHERE t.realReturnDate IS NULL) " +
          "ORDER BY r.nameBoat")
})
public class Resident extends Boat implements Serializable {
    private static final long serialVersionUID = 5427757221525641140L;
    public static final String FIND_ALL = "findAllResidents";
    public static final String FIND_MOORED = "findMooredResidents"; // amarrés
    
    public Resident() {}
    
    public Resident(Boat boat) { super(boat); }
    
    public Resident(String nameBoat, int serialNum, String insurance,
            Owner owner, Harbor harbor, Model model) {
        super(nameBoat, serialNum, insurance, owner, harbor, model);
    }
    
    @Override
    public String toString() {
        return "Resident [numBoat=" + numBoat + ", nameBoat=" + nameBoat
                + ", serialNum=" + serialNum + ", insurance=" + insurance
                + ", owner=" + owner + ", harbor=" + harbor
                + ", model=" + model + "]";
    }

}
