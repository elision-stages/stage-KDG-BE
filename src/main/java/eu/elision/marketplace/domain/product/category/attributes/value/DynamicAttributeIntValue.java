package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Used when the attribute has an integer value
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class DynamicAttributeIntValue extends DynamicAttributeValue<Integer> {
    public DynamicAttributeIntValue(Long id, String attributeName, int value) {
        super(id, attributeName, value);
    }

    public DynamicAttributeIntValue(String attributeName, Integer value) {
        super(attributeName, value);
    }
}
