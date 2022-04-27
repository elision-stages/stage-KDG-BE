package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.Getter;
import lombok.Setter;

/**
 * Used when the attribute has an integer value
 */
@Getter
@Setter
public class DynamicAttributeIntValue extends DynamicAttributeValue<Integer>
{
    public DynamicAttributeIntValue(String attributeName, int value) {
        super(attributeName, value);
    }
}
