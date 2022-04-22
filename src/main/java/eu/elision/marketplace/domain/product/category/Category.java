package eu.elision.marketplace.domain.product.category;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;

import java.util.List;

public class Category
{
    private String name;
    private List<Category> subCategories;
    private List<DynamicAttribute> characteristics;
}
