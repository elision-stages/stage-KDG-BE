package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;

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
