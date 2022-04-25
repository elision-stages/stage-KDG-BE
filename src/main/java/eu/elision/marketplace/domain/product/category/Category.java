package eu.elision.marketplace.domain.product.category;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to categorise orderLines
 */
@Getter

public class Category
{
    @Setter
    private String name;
    private final List<Category> subCategories;
    private final List<DynamicAttribute> characteristics;

    public Category() {
        subCategories = new ArrayList<>();
        characteristics = new ArrayList<>();
    }
}
