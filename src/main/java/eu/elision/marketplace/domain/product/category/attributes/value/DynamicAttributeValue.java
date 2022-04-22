package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains the name and value of the attribute of the category
 * @param <T> The value of the attribute
 */
@Getter
@Setter
public abstract class DynamicAttributeValue<T>
{
    private String attributeName;

    /**
     * Get the value of the attribute
     * @return the value in string format
     */
    public abstract T getValue();
}
