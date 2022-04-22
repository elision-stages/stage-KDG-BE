package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.product.Product;

import java.util.List;

public class Cart
{
    private List<Product> products;

    public double getTotalPrice()
    {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
