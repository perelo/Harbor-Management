package cas2.model;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@NamedQueries({
    // On aurais aimé la stocker, mais on veux récupérer
    // une liste d'emplacements, pas juste un seul
    @NamedQuery(name=Emplacement.FIND_EMPTY_BY_MODEL,
            query="SELECT e, ((et.length - et.length) + " + // = 0
                  "          ((:length   + :width)/2) / " +
                  "          ((et.length + et.width)/2) * 100) " +
                  "FROM Emplacement e join e.pontoon p, EmplacementType et " +
                  "WHERE e.empType = et " +
                  "  AND (p.isReserved = false           OR " +
                  "       p.isReserved = :isReserved) " +
                  "  AND et.length >= :length " +
                  "  AND et.width  >= :width " +
                  "  AND (e.numEmp NOT IN" +
                  "                (SELECT emp1.numEmp FROM " +
                  "                 Occupation occ join occ.emplacement emp1) " +
                  "   OR  e.numEmp     IN" +
                  "                (SELECT e1.numEmp " +
                  "              FROM Occupation o join o.emplacement e1, " +
                  "                   Trip t " +
                  "              WHERE o.startOccupationDate >= ALL " +
                  "                            (SELECT o1.startOccupationDate " +
                  "                          FROM Occupation o1 " +
                  "                             WHERE o1.emplacement = " +
                  "                                            o.emplacement)" +
                  "                AND (FUNC('ADD_MONTHS', " +
                  "                                o.startOccupationDate," +
                  "                                o.occupationDuration) < " +
                  "                                            CURRENT_DATE) " +
                  "                 OR (t.boat = o.resident AND " +
                  "                     t.realReturnDate IS NULL AND " +
                  "                     t.plannedReturnDate > " +
                  "                            FUNC('TO_DATE', CURRENT_DATE) + " +
                  "                                    :parkingDuration AND " +
                  "                     :isReserved = true))) " +
                  "  AND (e.numEmp NOT IN " +
                  "                (SELECT emp2.numEmp FROM " +
                  "              Parking park join park.emplacement emp2) " +
                  "   OR  e.numEmp     IN " +
                  "             (SELECT e2.numEmp " +
                  "              FROM Parking pa join pa.emplacement e2 " +
                  "              WHERE FUNC('TO_DATE', pa.startParkingDate) " +
                  "                                    + pa.parkingDuration < " +
                  "                                            CURRENT_DATE)) " +
                  "ORDER BY p.isReserved DESC")
})
public class Emplacement implements Serializable, UserEditable{
    private static final long serialVersionUID = -1690313001549025821L;
    public static final String FIND_EMPTY_BY_MODEL =
                                            "findEmptyEmplacementsByModel";

    @Id
    @GeneratedValue
    @Column (name="NUM_EMP")
    private int numEmp;
    
    @ManyToOne
    @JoinColumn (name="EMP_TYPE", nullable=false)
    private EmplacementType empType;
    @ManyToOne
    @JoinColumn (name="NUM_PONTOON", nullable=false)
    private Pontoon pontoon;
    @Column (name="NUM_EMP_PONTOON", nullable=false)
    private int numEmpPontoon; // le numero d'emplacement sur le ponton
    
    public Emplacement() {}
    
    public Emplacement(EmplacementType empType, Pontoon pontoon,
            int numEmpPontoon) {
        super();
        this.empType = empType;
        this.pontoon = pontoon;
        this.numEmpPontoon = numEmpPontoon;
    }

    @Override
    public String toString() {
        return "Emplacement [numEmp=" + numEmp + ", empType=" + empType
                + ", pontoon=" + pontoon + ", numEmpPontoon=" + numEmpPontoon
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numEmp;
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
        Emplacement other = (Emplacement) obj;
        if (numEmp != other.numEmp)
            return false;
        return true;
    }

    public EmplacementType getEmpType() {
        return empType;
    }

    public void setEmpType(EmplacementType empType) {
        this.empType = empType;
    }

    public Pontoon getPontoon() {
        return pontoon;
    }

    public void setPontoon(Pontoon pontoon) {
        this.pontoon = pontoon;
    }

    public int getNumEmpPontoon() {
        return numEmpPontoon;
    }

    public void setNumEmpPontoon(int numEmpPontoon) {
        this.numEmpPontoon = numEmpPontoon;
    }

    public int getNumEmp() {
        return numEmp;
    }

    @Override
    public String toStringForUsers() {
        return pontoon.toStringForUsers() + " (" + numEmpPontoon + ")";
    }

}
