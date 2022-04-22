package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * An orderline contains the product and the quantity and the ordernumber of the order it belongs to.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLine
{
    private int orderLineNumber;
    private Vendor vendor;
    private String ordernumber;
    private Product product;
    private int quantity;

    /**
     * Get the total price of the order line
     * @return the total price of the order line
     */
    public double getTotalPrice(){
        return product.getPrice() * quantity;
    }
}
