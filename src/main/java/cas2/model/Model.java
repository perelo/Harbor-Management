package cas2.model;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name=Model.FIND_ALL,
                query="SELECT m FROM Model m ORDER BY (m.length + m.width)/2")
})
public class Model implements Serializable, UserEditable {
    private static final long serialVersionUID = -1823662869536893245L;
    public static final String FIND_ALL = "findAllModels";
    
    @Id
    @GeneratedValue
    @Column(name="NUM_MODEL")
    private int numModel;
    
    @Column (name="SERIE_MODEL")
    private String serie;
    @Column (name="TYPE_MODEL")
    private int type;
    private String constructor;
    @Column (name="LENGTH_MODEL")
    private int length;
    @Column (name="WIDTH_MODEL")
    private int width;
    @Column (name="DRAFT_MODEL")
    private int draft;
    
    public Model () {}

    public Model(String serie, int type, String constructor, int length,
            int width, int draft) {
        super();
        // un trigger verifie qu'il n'existe pas deja un Model
        // pour cette serie et ce type
        this.serie = serie;
        this.type = type;
        this.constructor = constructor;
        this.length = length;
        this.width = width;
        this.draft = draft;
    }

    @Override
    public String toString() {
        return "Model [numModel=" + numModel
                + (serie != null ? ", serie=" + serie : "")
                + ", type=" + type
                + (constructor != null ? ", constructor=" + constructor : "")
                + ", length=" + length + ", width=" + width
                + ", draft=" + draft + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numModel;
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
        Model other = (Model) obj;
        if (numModel != other.numModel)
            return false;
        return true;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getConstructor() {
        return constructor;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDraft() {
        return draft;
    }

    public void setDraft(int draft) {
        this.draft = draft;
    }

    public int getNumModel() {
        return numModel;
    }

    @Override
    public String toStringForUsers() {
        return serie + " (length=" + length + ", width=" + width + ")";
    }
    
}