package cas2.model;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name=Harbor.FIND_ALL,
                query="SELECT h FROM Harbor h ORDER BY h.codeHarbor")
})
public class Harbor implements Serializable, UserEditable {
    private static final long serialVersionUID = 3569198162561182759L;
    public static final String FIND_ALL = "findAllHarbors";
    
    @Id
    @GeneratedValue
    @Column (name="NUM_HARBOR")
    private int numHarbor;
    
    @Column (name="CODE_HARBOR", nullable=false)
    private String codeHarbor;
    @Column (name="NAME_HARBOR")
    private String name;
    
    public Harbor () {}

    public Harbor(String codeHarbor, String name) {
        super();
        // un trigger verifie qu'il n'existe pas deja un HARBOR
        // qui porte ce code
        this.codeHarbor = codeHarbor;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Harbor [numHarbor=" + numHarbor + ", codeHarbor=" + codeHarbor
                + (name != null ? ", name=" + name : "") + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numHarbor;
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
        Harbor other = (Harbor) obj;
        if (numHarbor != other.numHarbor)
            return false;
        return true;
    }

    public String getCodeHarbor() {
        return codeHarbor;
    }

    public void setCodeHarbor(String codeHarbor) {
        this.codeHarbor = codeHarbor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumHarbor() {
        return numHarbor;
    }

    @Override
    public String toStringForUsers() {
        return name + " (" + codeHarbor + ")";
    }
    
}