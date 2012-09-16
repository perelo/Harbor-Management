package cas2.model;
import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Owner implements Serializable, UserEditable {
    private static final long serialVersionUID = 1780701082650743630L;

    @Id
    @GeneratedValue
    @Column (name="NUM_OWNER")
    private int numOwner;
    @Column (name="NAME_OWNER")
    private String nameOwner;
    @Column (name="PHONE_OWNER")
    private String phone;

    @Embedded
    private Address address;

    public Owner() {}
    
    public Owner (Owner owner) {
        this.nameOwner = owner.getNameOwner();
        this.phone = owner.getPhone();
        this.address = owner.getAddress();
    }

    public Owner(String nameOwner, String phone, Address address) {
        super();
        this.nameOwner = nameOwner;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Owner [nameOwner=" + nameOwner
                + (phone != null ? ", phone=" + phone : "")
                + (address != null ? ", address=" + address : "") + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numOwner;
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
        Owner other = (Owner) obj;
        if (numOwner != other.numOwner)
            return false;
        return true;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getNumOwner() {
        return numOwner;
    }

    @Override
    public String toStringForUsers() {
        return nameOwner + " (" + numOwner + ")";
    }

}
