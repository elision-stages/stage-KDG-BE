package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.Getter;
import lombok.Setter;

/**
 * Used when the attribute has an enum value
 */
@Getter
@Setter
public class DynamicAttributeEnumValue extends DynamicAttributeValue<String>
{
    public DynamicAttributeEnumValue(String attributeName, String value) {
        super(attributeName, value);
    }
}
