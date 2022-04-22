package eu.elision.marketplace.domain.product.category.attributes.value;

import eu.elision.marketplace.domain.product.category.attributes.Type;

public abstract class DynamicAttributeValue
{
    private String attributeName;

    /**
     * Get the value of the attribute
     * @return the value in string format
     */
    public abstract String getValue();
}
