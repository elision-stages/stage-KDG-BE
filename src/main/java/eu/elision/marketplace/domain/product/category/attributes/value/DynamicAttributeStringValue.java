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
public class DynamicAttributeStringValue extends DynamicAttributeValue<String> {
    private String value;

    /**
     * Public constructor
     *
     * @param id            the id of the attribute value
     * @param attributeName the name of the attribute
     * @param value         the value of the attribute
     */
    public DynamicAttributeStringValue(Long id, String attributeName, String value) {
        super(id, attributeName);
        this.value = value;
    }

    /**
     * Public constructor without id
     *
     * @param attributeName the name oif the attribute
     * @param value         the value of the attribute
     */
    public DynamicAttributeStringValue(String attributeName, String value) {
        super(attributeName);
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
