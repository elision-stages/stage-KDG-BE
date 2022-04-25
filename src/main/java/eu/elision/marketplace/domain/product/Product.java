package eu.elision.marketplace.domain.product;

import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This product contains the info of a product
 */
@Data @AllArgsConstructor
public class Product
{
    private double price;
    private Vendor vendor;
    private String description;
    private List<String> images;

    private List<DynamicAttributeValue<?>> attributes;

    public Product() {
        this.images = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }
}
