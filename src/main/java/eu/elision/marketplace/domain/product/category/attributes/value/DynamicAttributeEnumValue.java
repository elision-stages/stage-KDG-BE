package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Used when the attribute has an enum value
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class DynamicAttributeEnumValue extends DynamicAttributeValue<String> {
    public DynamicAttributeEnumValue(Long id, String attributeName, String value) {
        super(id, attributeName, value);
    }

    public DynamicAttributeEnumValue(String attributeName, String value) {
        super(attributeName, value);
    }
}
