package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The order class contains the orderNumber, orderlines and shipping address of an order. When the user is a vendor the orderlines should only be of prodructs from the vendor.
 */
@Data
@Entity
@Table(name = "orders")
public class Order
{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String orderNumber;
    @ManyToOne
    private User user;
    @OneToOne
    private Address shippingAddress;
    @OneToMany
    private List<OrderLine> lines;

    public Order()
    {
        lines = new ArrayList<>();
    }

    /**
     * Get the total price of an order
     * @return the total price of an order
     */
    public double getTotalPrice(){
        return lines.stream().mapToDouble(OrderLine::getTotalPrice).sum();
    }
}
