package eu.elision.marketplace.domain.product;

import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import lombok.Getter;

import java.util.List;

public class Product
{
    @Getter
    private double price;
    private Vendor vendor;
    private String description;
    private List<String> images;

    private List<DynamicAttributeValue> attributes;
}
