package eu.elision.marketplace.domain.users;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all of the extra information of a customer
 */
@Getter
@Setter
@Entity
public class Customer extends User{
    @OneToOne
    private Cart cart;
    @OneToOne
    private Address mainAddress;
    @OneToMany
    private List<Address> otherAddresses;

    public Customer() {
        this.otherAddresses = new ArrayList<>();
    }
}
