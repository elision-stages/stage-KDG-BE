package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Used when an attribute has a double value
 */
@Setter
@NoArgsConstructor
@Entity
public class DynamicAttributeDoubleValue extends DynamicAttributeValue<Double>
{
    private double value;

    public DynamicAttributeDoubleValue(Long id, String attributeName, double value)
    {
        super(id, attributeName);
        this.value = value;
    }

    public DynamicAttributeDoubleValue(String attributeName, double value)
    {
        super(attributeName);
        this.value = value;
    }

    @Override
    public Double getValue()
    {
        return this.value;
    }
}
