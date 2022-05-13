package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Used when the attribute has an integer value
 */
@Setter
@Entity
@NoArgsConstructor
public class DynamicAttributeIntValue extends DynamicAttributeValue<Integer>
{
    private int value;

    public DynamicAttributeIntValue(Long id, String attributeName, int value)
    {
        super(id, attributeName);
        this.value = value;
    }

    public DynamicAttributeIntValue(String attributeName, Integer value)
    {
        super(attributeName);
        this.value = value;
    }

    @Override
    public Integer getValue()
    {
        return this.value;
    }
}
