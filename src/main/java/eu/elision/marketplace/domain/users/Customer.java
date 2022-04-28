package eu.elision.marketplace.domain.users;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all of the extra information of a customer
 */
@Getter
@Setter
public class Customer extends User{
    private Cart cart;
    private Address mainAddress;
    private List<Address> otherAddresses;

    public Customer() {
        this.otherAddresses = new ArrayList<>();
    }
}
