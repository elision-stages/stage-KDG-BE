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

    /**
     * All args constructor.
     *
     * @param orderLineNumber the number of the orderline. Is the id in the database
     * @param vendor          the vendor of the product
     * @param orderNumber     the ordernumber the order line is connected to
     * @param product         the product in the
     * @param quantity        the quantity of the product
     */
    public OrderLine(int orderLineNumber, Vendor vendor, String orderNumber, Product product, int quantity) {
        this.orderLineNumber = orderLineNumber;
        this.vendor = vendor;
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
