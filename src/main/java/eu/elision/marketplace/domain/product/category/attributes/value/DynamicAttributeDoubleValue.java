package eu.elision.marketplace.domain.product.category.attributes.value;

public class DynamicAttributeDoubleValue extends DynamicAttributeValue
{
    private double value;

    @Override
    public String getValue()
    {
        return String.valueOf(value);
    }
}
