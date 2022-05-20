package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * No args constructor. Initalises order lines array
     */
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

    /**
     * Add a product to a cart
     *
     * @param product  the product you want to add
     * @param quantity the amount of products you want to add
     * @param add      if true, the quantity will be added to the quantity if there is already a order line with the same proudct. If false the previous quantity will be overwritten
     */
    public void addProduct(Product product, int quantity, boolean add) {
        for (OrderLine ol : orderLines) {
            if (Objects.equals(ol.getProduct().getId(), product.getId())) {
                int newQuantity = add ? ol.getQuantity() + quantity : quantity;
                if (newQuantity == 0) {
                    orderLines.remove(ol);
                    return;
                }
                ol.setQuantity(newQuantity);
                return;
            }
        }

        final OrderLine orderLine = new OrderLine();
        orderLine.setProduct(product);
        orderLine.setQuantity(quantity);

        orderLines.add(orderLine);
    }

    /**
     * Make an order from the shopping cart of a user
     *
     * @param user the user who wants to checkout
     * @return the order created from the cart
     */
    public Order checkout(Customer user) {
        if (orderLines.isEmpty()) return null;
        Order order = new Order();
        order.getLines().addAll(orderLines);
        order.setUser(user);
        orderLines.forEach(orderLine -> orderLine.setOrderNumber(String.valueOf(order.getOrderNumber())));

        orderLines.clear();

        return order;
    }
}
