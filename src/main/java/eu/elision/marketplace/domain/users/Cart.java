package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.orders.OrderLine;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The cart contains the orderLines of an customer that aren't bought yet
 */
@Getter
public class Cart
{
    private final List<OrderLine> orderLines;

    public Cart()
    {
        orderLines = new ArrayList<>();
    }

    /**
     * Get the total price of a cart
     * @return the value of all the orderLines in the cart
     */
    public double getTotalPrice()
    {
        return orderLines.stream().mapToDouble(OrderLine::getTotalPrice).sum();
    }
}
