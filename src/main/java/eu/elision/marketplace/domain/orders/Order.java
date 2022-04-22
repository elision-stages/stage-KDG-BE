package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.User;
import lombok.Getter;

import java.util.List;

public class Order
{
    private String ordernumber;
    private User user;
    private Address shippingAddress;
    @Getter
    private List<OrderLine> lines;

    public double getTotalPrice(){
        return lines.stream().mapToDouble(OrderLine::getTotalPrice).sum();
    }
}
