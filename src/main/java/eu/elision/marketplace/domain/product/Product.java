package eu.elision.marketplace.domain.product;

import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * This product contains the info of a product
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product
{
    private double price;
    private Vendor vendor;
    private String description;
    private List<String> images;

    private List<DynamicAttributeValue<?>> attributes;
}
