package eu.elision.marketplace.domain.product.category;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to categorise orderLines
 */
@Getter
@Setter
public class Category
{
    private String name;
    private List<Category> subCategories;
    private List<DynamicAttribute> characteristics;
}
