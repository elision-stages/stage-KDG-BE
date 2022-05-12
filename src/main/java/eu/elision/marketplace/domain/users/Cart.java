package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The cart contains the orderLines of an customer that aren't bought yet
 */
@Getter
@Entity

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<OrderLine> orderLines;

    public Cart() {
        orderLines = new ArrayList<>();
    }

    /**
     * Get the total price of a cart
     *
     * @return the value of all the orderLines in the cart
     */
    public double getTotalPrice() {
        return orderLines.stream().mapToDouble(OrderLine::getTotalPrice).sum();
    }

    public void addProduct(Product product, int quantity) {
        final OrderLine orderLine = new OrderLine();
        orderLine.setProduct(product);
        orderLine.setQuantity(quantity);

        orderLines.add(orderLine);
    }
}
