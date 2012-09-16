package cas2.model;

import java.io.Serializable;

import javax.persistence.*;

@Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = -8632041936970148587L;
    
    @Column (name="STREET_NAME")
    private String streetName;
    @Column (name="STREET_NUM")
    private Integer streetNum; // numero dans la rue
    @Column (name="ZIP_CODE")
    private Integer zipCode;
    @Column (name="CITY")
    private String city;
    
    public Address() {}

    public Address(String streetName, int streetNum,
                   int zipCode, String city) {
        super();
        this.streetName = streetName;
        this.streetNum = streetNum;
        this.zipCode = zipCode;
        this.city = city;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result
                + ((streetName == null) ? 0 : streetName.hashCode());
        result = prime * result + streetNum;
        result = prime * result + zipCode;
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
        Address other = (Address) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (streetName == null) {
            if (other.streetName != null)
                return false;
        } else if (!streetName.equals(other.streetName))
            return false;
        if (streetNum != other.streetNum)
            return false;
        if (zipCode != other.zipCode)
            return false;
        return true;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
