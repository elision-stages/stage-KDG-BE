package eu.elision.marketplace.domain.product.category.attributes.value;

import javax.persistence.Entity;

/**
 * Used when the attribute has an integer value
 */
@Entity
public class DynamicAttributeIntValue extends DynamicAttributeValue<Integer> {
    public DynamicAttributeIntValue(Long id, String attributeName, int value) {
        super(id, attributeName, value);
    }

    @SuppressWarnings("all")
    public DynamicAttributeIntValue() {
    }
}
