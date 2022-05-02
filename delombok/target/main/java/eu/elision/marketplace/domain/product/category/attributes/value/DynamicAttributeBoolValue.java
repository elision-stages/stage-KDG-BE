package eu.elision.marketplace.domain.product.category.attributes.value;

import javax.persistence.Entity;

/**
 * Used when the attribute is a boolean
 */
@Entity
public class DynamicAttributeBoolValue extends DynamicAttributeValue<Boolean> {
    public DynamicAttributeBoolValue(Long id, String attributeName, Boolean value) {
        super(id, attributeName, value);
    }

    @SuppressWarnings("all")
    public DynamicAttributeBoolValue() {
    }
}
