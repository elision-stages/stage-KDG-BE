package eu.elision.marketplace.domain.product.category.attributes.value;

import eu.elision.marketplace.domain.product.category.attributes.Type;

public abstract class DynamicAttributeValue
{
    private String attributeName;

    public abstract String getValue();
}
