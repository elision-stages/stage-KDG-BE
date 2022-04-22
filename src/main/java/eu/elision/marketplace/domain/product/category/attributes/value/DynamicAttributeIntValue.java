package eu.elision.marketplace.domain.product.category.attributes.value;

public class DynamicAttributeIntValue extends DynamicAttributeValue
{
    private int value;

    @Override
    public String getValue()
    {
        return String.valueOf(value);
    }
}
