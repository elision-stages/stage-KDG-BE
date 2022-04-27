package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.Getter;
import lombok.Setter;

/**
 * Used when an attribute has a double value
 */
@Getter
@Setter
public class DynamicAttributeDoubleValue extends DynamicAttributeValue<Double> {

    public DynamicAttributeDoubleValue(String attributeName, double value) {
        super(attributeName, value);
    }
}
