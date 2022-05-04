package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Used when the attribute is a boolean
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class DynamicAttributeBoolValue extends DynamicAttributeValue<Boolean> {
    public DynamicAttributeBoolValue(Long id, String attributeName, Boolean value) {
        super(id, attributeName, value);
    }

    public DynamicAttributeBoolValue(String attributeName, Boolean value) {
        super(attributeName, value);
    }
}
