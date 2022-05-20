package eu.elision.marketplace.domain.users;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the extra information of a customer
 */
@Getter
@Setter
@Entity
public class Customer extends User {
    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    /**
     * No args constructor. Initialises other adresses array and cart
     */
    public Customer() {
        this.cart = new Cart();
    }
}
