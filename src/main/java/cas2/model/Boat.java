package cas2.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Inheritance (strategy=InheritanceType.JOINED)
@DiscriminatorColumn (name="BOAT_TYPE")
@NamedQueries({
    @NamedQuery(name=Boat.FIND_ALL,
                query="SELECT b FROM Boat b ORDER BY b.nameBoat"),
    @NamedQuery(name=Boat.COMPUTE_NB_BOATS_BY_OWNER,
                query="SELECT o, count(b) " +
                      "FROM Boat b join b.owner o " +
                      "GROUP BY o"),
    @NamedQuery(name=Boat.COMPUTE_NB_BOATS_BY_MODEL,
                query="SELECT m, count(b) " +
                        "FROM Boat b join b.model m " +
                      "GROUP BY m")
})
public class Boat implements Serializable, UserEditable {
    private static final long serialVersionUID = -4194910462213491466L;
    public static final String FIND_ALL = "findAllBoats";
    public static final String COMPUTE_NB_BOATS_BY_OWNER =
                                                    "computeNbBoatsByOwner";
    public static final String COMPUTE_NB_BOATS_BY_MODEL = 
                                                    "computeNbBoatsByModel";
    
    @Id
    @GeneratedValue
    @Column (name="NUM_BOAT")
    protected int numBoat;
    
    @Column (name="NAME_BOAT", nullable=false)
    protected String nameBoat;
    @Column (name="SERIAL_NUM")
    protected int serialNum;
    protected String insurance;

    @ManyToOne
    @JoinColumn (name="NUM_OWNER", nullable=false)
    protected Owner owner;
    @ManyToOne
    @JoinColumn (name="NUM_HARBOR", nullable=false)
    protected Harbor harbor;
    @ManyToOne
    @JoinColumn (name="NUM_MODEL", nullable=false)
    protected Model model;
    
    public Boat() {}
    
    public Boat(String nameBoat, int serialNum, String insurance,
            Owner owner, Harbor harbor, Model model) {
        super();
        this.nameBoat = nameBoat;
        this.serialNum = serialNum;
        this.insurance = insurance;
        this.owner = owner;
        this.harbor = harbor;
        this.model = model;
    }
    
    public Boat(Boat boat) {
        this(boat.getNameBoat(),
             boat.getSerialNum(),
             boat.getInsurance(),
             boat.getOwner(),
             boat.getHarbor(),
             boat.getModel());
    }

    @Override
    public String toString() {
        return "Boat [num=" + numBoat + ", name=" + nameBoat + ", serialNum="
                + serialNum + ", insurance=" + insurance
                + ", owner=" + owner + ", harbor=" + harbor
                + ", model=" + model + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numBoat;
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
        Boat other = (Boat) obj;
        if (numBoat != other.numBoat)
            return false;
        return true;
    }

    public String getNameBoat() {
        return nameBoat;
    }

    public void setNameBoat(String nameBoat) {
        this.nameBoat = nameBoat;
    }

    public int getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Harbor getHarbor() {
        return harbor;
    }

    public void setHarbor(Harbor harbor) {
        this.harbor = harbor;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getNumBoat() {
        return numBoat;
    }
    
    @Override
    public String toStringForUsers() {
        return nameBoat + " (" + numBoat + ")";
    }
}
