package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.Getter;
import lombok.Setter;

/**
 * Used when the attribute is a boolean
 */
@Getter @Setter
public class DynamicAttributeBoolValue extends DynamicAttributeValue <Boolean>
{
    private boolean value;

    public DynamicAttributeBoolValue(String name, boolean value) {
        super(name);
        this.value = value;
    }

    @Override
    public Boolean getValue()
    {
        return value;
    }
}
