package eu.elision.marketplace.web.dtos.attributes;

import eu.elision.marketplace.domain.product.category.attributes.Type;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto to transfer data about dynamic attributes
 */
@Getter
@Setter
public class DynamicAttributeDto
{
    private String name;
    private boolean required;
    private Type type;

    /**
     * Public constructor
     *
     * @param name     the name of the attribute
     * @param required if the attribute is required
     * @param type     the type of the attribute
     */
    public DynamicAttributeDto(String name, boolean required, Type type) {
        this.name = name;
        this.required = required;
        this.type = type;
    }

    public DynamicAttributeDto() {

    }
}
