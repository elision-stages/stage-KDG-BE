package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Used when the attribute is a boolean
 */
@Setter
@NoArgsConstructor
@Entity
public class DynamicAttributeBoolValue extends DynamicAttributeValue<Boolean>
{
    private boolean value;

    /**
     * Public constructor
     *
     * @param id            the id of the dynamic attribute
     * @param attributeName the name of the dynamic attribute
     * @param value         the value of the dynamic attribute
     */
    public DynamicAttributeBoolValue(Long id, String attributeName, Boolean value)
    {
        super(id, attributeName);
        this.value = value;
    }

    /**
     * Public constructor without id
     *
     * @param attributeName the name of the dynamic attribute
     * @param value         the value of the dynamic attribute
     */
    public DynamicAttributeBoolValue(String attributeName, Boolean value)
    {
        super(attributeName);
        this.value = value;
    }

    @Override
    public Boolean getValue()
    {
        return this.value;
    }
}
