package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Used when the attribute has an enum value
 */
@Setter
@Entity
@NoArgsConstructor
public class DynamicAttributeEnumValue extends DynamicAttributeValue<String> {
    private String value;

    public DynamicAttributeEnumValue(Long id, String attributeName, String value)
    {
        super(id, attributeName);
        this.value = value;
    }

    public DynamicAttributeEnumValue(String attributeName, String value)
    {
        super(attributeName);
        this.value = value;
    }

    @Override
    public String getValue()
    {
        return this.value;
    }
}
