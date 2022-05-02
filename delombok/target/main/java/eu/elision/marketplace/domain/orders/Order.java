package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.User;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The order class contains the orderNumber, orderlines and shipping address of an order. When the user is a vendor the orderlines should only be of prodructs from the vendor.
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String orderNumber;
    @ManyToOne
    private User user;
    @OneToOne
    private Address shippingAddress;
    @OneToMany
    private List<OrderLine> lines;

    public Order() {
        lines = new ArrayList<>();
    }

    /**
     * Get the total price of an order
     * @return the total price of an order
     */
    public double getTotalPrice() {
        return lines.stream().mapToDouble(OrderLine::getTotalPrice).sum();
    }

    @SuppressWarnings("all")
    public String getOrderNumber() {
        return this.orderNumber;
    }

    @SuppressWarnings("all")
    public User getUser() {
        return this.user;
    }

    @SuppressWarnings("all")
    public Address getShippingAddress() {
        return this.shippingAddress;
    }

    @SuppressWarnings("all")
    public List<OrderLine> getLines() {
        return this.lines;
    }

    @SuppressWarnings("all")
    public void setOrderNumber(final String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @SuppressWarnings("all")
    public void setUser(final User user) {
        this.user = user;
    }

    @SuppressWarnings("all")
    public void setShippingAddress(final Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @SuppressWarnings("all")
    public void setLines(final List<OrderLine> lines) {
        this.lines = lines;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Order)) return false;
        final Order other = (Order) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$orderNumber = this.getOrderNumber();
        final Object other$orderNumber = other.getOrderNumber();
        if (this$orderNumber == null ? other$orderNumber != null : !this$orderNumber.equals(other$orderNumber)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$shippingAddress = this.getShippingAddress();
        final Object other$shippingAddress = other.getShippingAddress();
        if (this$shippingAddress == null ? other$shippingAddress != null : !this$shippingAddress.equals(other$shippingAddress)) return false;
        final Object this$lines = this.getLines();
        final Object other$lines = other.getLines();
        if (this$lines == null ? other$lines != null : !this$lines.equals(other$lines)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof Order;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $orderNumber = this.getOrderNumber();
        result = result * PRIME + ($orderNumber == null ? 43 : $orderNumber.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $shippingAddress = this.getShippingAddress();
        result = result * PRIME + ($shippingAddress == null ? 43 : $shippingAddress.hashCode());
        final Object $lines = this.getLines();
        result = result * PRIME + ($lines == null ? 43 : $lines.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "Order(orderNumber=" + this.getOrderNumber() + ", user=" + this.getUser() + ", shippingAddress=" + this.getShippingAddress() + ", lines=" + this.getLines() + ")";
    }
}
