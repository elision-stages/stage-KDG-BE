package eu.elision.marketplace.domain.users;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all of the extra information of a customer
 */
@Entity
public class Customer extends User {
    @OneToOne
    private Cart cart;
    @OneToOne
    private Address mainAddress;
    @OneToMany
    private List<Address> otherAddresses;

    public Customer() {
        this.otherAddresses = new ArrayList<>();
    }

    @SuppressWarnings("all")
    public Cart getCart() {
        return this.cart;
    }

    @SuppressWarnings("all")
    public Address getMainAddress() {
        return this.mainAddress;
    }

    @SuppressWarnings("all")
    public List<Address> getOtherAddresses() {
        return this.otherAddresses;
    }

    @SuppressWarnings("all")
    public void setCart(final Cart cart) {
        this.cart = cart;
    }

    @SuppressWarnings("all")
    public void setMainAddress(final Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    @SuppressWarnings("all")
    public void setOtherAddresses(final List<Address> otherAddresses) {
        this.otherAddresses = otherAddresses;
    }
}
