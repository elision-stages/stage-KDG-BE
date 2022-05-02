package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import lombok.Data;

import javax.persistence.*;

/**
 * An orderline contains the product and the quantity and the ordernumber of the order it belongs to.
 */
@Data
@Entity
public class OrderLine
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int orderLineNumber;
    @ManyToOne
    private Vendor vendor;
    private String orderNumber;
    @ManyToOne
    private Product product;
    private int quantity;

    public OrderLine(int orderLineNumber, Vendor vendor, String orderNumber, Product product, int quantity)
    {
        this.orderLineNumber = orderLineNumber;
        this.vendor = vendor;
        this.orderNumber = orderNumber;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderLine()
    {
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
