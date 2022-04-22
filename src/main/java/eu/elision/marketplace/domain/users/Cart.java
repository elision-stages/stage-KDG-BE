package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.product.Product;

import java.util.List;

public class Cart
{
    private List<Product> products;

    /**
     * Get the total price of a cart
     * @return the value of all the products in the cart
     */
    public double getTotalPrice()
    {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
