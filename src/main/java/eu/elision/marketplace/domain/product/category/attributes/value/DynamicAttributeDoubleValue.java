package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Used when an attribute has a double value
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class DynamicAttributeDoubleValue extends DynamicAttributeValue<Double> {

    public DynamicAttributeDoubleValue(Long id, String attributeName, double value) {
        super(id, attributeName, value);
    }

    public DynamicAttributeDoubleValue(String attributeName, double value) {
        super(attributeName, value);
    }
}
