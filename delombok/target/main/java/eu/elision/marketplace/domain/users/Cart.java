package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.orders.OrderLine;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The cart contains the orderLines of an customer that aren't bought yet
 */
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private final List<OrderLine> orderLines;

    public Cart() {
        orderLines = new ArrayList<>();
    }

    /**
     * Get the total price of a cart
     * @return the value of all the orderLines in the cart
     */
    public double getTotalPrice() {
        return orderLines.stream().mapToDouble(OrderLine::getTotalPrice).sum();
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public List<OrderLine> getOrderLines() {
        return this.orderLines;
    }
}
