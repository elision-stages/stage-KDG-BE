package eu.elision.marketplace.domain.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class contains the info of an address
 */
@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String street;
    private String number;
    private String postalCode;
    private String city;

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getStreet() {
        return this.street;
    }

    @SuppressWarnings("all")
    public String getNumber() {
        return this.number;
    }

    @SuppressWarnings("all")
    public String getPostalCode() {
        return this.postalCode;
    }

    @SuppressWarnings("all")
    public String getCity() {
        return this.city;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setStreet(final String street) {
        this.street = street;
    }

    @SuppressWarnings("all")
    public void setNumber(final String number) {
        this.number = number;
    }

    @SuppressWarnings("all")
    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    @SuppressWarnings("all")
    public void setCity(final String city) {
        this.city = city;
    }
}
