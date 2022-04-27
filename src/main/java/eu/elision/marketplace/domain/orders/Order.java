package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The order class contains the orderNumber, orderlines and shipping address of an order. When the user is a vendor the orderlines should only be of prodructs from the vendor.
 */
@Data
public class Order
{
    private String orderNumber;
    private User user;
    private Address shippingAddress;
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
