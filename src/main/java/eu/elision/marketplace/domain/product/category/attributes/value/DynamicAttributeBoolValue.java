package eu.elision.marketplace.domain.product.category.attributes.value;

public class DynamicAttributeBoolValue extends DynamicAttributeValue
{
    private boolean value;

    @Override
    public String getValue()
    {
        return value ? "Ja" : "Nee";
    }
}
