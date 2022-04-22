package eu.elision.marketplace.domain.product.category.attributes.value;

import eu.elision.marketplace.domain.product.category.attributes.PickListItem;

import java.util.List;

public class DynamicAttributeEnumValue extends DynamicAttributeValue
{
    private String value;

    @Override
    public String getValue()
    {
        return value;
    }
}
