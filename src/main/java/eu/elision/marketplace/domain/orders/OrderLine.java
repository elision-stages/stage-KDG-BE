package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.product.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * An order line contains the product and the quantity and the order number of the order it belongs to.
 */
@Getter
@Setter
@Entity
public class OrderLine
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int orderLineNumber;
    private String orderNumber;
    @ManyToOne
    private Product product;
    private int quantity;

    /**
     * All args constructor.
     *
     * @param orderLineNumber the number of the order line. Is the id in the database
     * @param orderNumber     the order number the order line is connected to
     * @param product         the product in the
     * @param quantity        the quantity of the product
     */
    public OrderLine(int orderLineNumber, String orderNumber, Product product, int quantity) {
        this.orderLineNumber = orderLineNumber;
        this.orderNumber = orderNumber;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderLine() {
    }

    /**
     * Get the total price of the order line
     *
     * @return the total price of the order line
     */
    public double getTotalPrice()
    {
        return product.getPrice() * quantity;
    }
}
