package eu.elision.marketplace.domain.product.category.attributes.value;

import javax.persistence.Entity;

/**
 * Used when an attribute has a double value
 */
@Entity
public class DynamicAttributeDoubleValue extends DynamicAttributeValue<Double> {
    public DynamicAttributeDoubleValue(Long id, String attributeName, double value) {
        super(id, attributeName, value);
    }

    @SuppressWarnings("all")
    public DynamicAttributeDoubleValue() {
    }
}
