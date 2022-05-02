package eu.elision.marketplace.domain.product.category.attributes.value;

import javax.persistence.Entity;

/**
 * Used when the attribute has an enum value
 */
@Entity
public class DynamicAttributeEnumValue extends DynamicAttributeValue<String> {
    public DynamicAttributeEnumValue(Long id, String attributeName, String value) {
        super(id, attributeName, value);
    }

    @SuppressWarnings("all")
    public DynamicAttributeEnumValue() {
    }
}
