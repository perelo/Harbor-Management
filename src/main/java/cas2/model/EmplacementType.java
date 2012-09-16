package cas2.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table (name="EMP_TYPE")
public class EmplacementType implements Serializable, UserEditable {
    private static final long serialVersionUID = -4358315260658095307L;
    
    @Id
    @GeneratedValue
    @Column (name="NUM_EMP_TYPE")
    private int numType;
    @Column (name="LENGTH_EMP")
    private int length;
    @Column (name="WIDTH_EMP")
    private int width;
    @Column (name="DEPTH_EMP")
    private int depth;

    public EmplacementType () {}

    public EmplacementType(int length, int width, int depth) {
        // un trigger vérifie que ce triplet est unique
        super();
        this.length = length;
        this.width = width;
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "Emplacement_Type [numType=" + numType + ", "
                + "length=" + length
                + ", width=" + width + ", depth=" + depth + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numType;
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
        EmplacementType other = (EmplacementType) obj;
        if (numType != other.numType)
            return false;
        return true;
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

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getNumType() {
        return numType;
    }

    @Override
    public String toStringForUsers() {
        return numType + " (" + length + ", " + width + ")";
    }
    
}
