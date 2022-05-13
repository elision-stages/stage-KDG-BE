package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.users.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The order class contains the orderNumber, orderLines and shipping address of an order. When the user is a vendor the orderLines should only be of products from the vendor.
 */
@Data
@Entity
@Table(name = "orders")
@SequenceGenerator(name = "sequence", sequenceName = "mySequence", initialValue = 100)
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long orderNumber;
    @ManyToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLine> lines;

    public Order()
    {
        lines = new ArrayList<>();
    }

    /**
     * Get the total price of an order
     *
     * @return the total price of an order
     */
    public double getTotalPrice()
    {
        return lines.stream().mapToDouble(OrderLine::getTotalPrice).sum();
    }
}
