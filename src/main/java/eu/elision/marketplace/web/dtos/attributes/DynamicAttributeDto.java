package eu.elision.marketplace.web.dtos.attributes;

import eu.elision.marketplace.domain.product.category.attributes.Type;

import java.io.Serializable;

/**
 * Dto to transfer data about dynamic attributes
 *
 * @param name     the name of the attribute
 * @param required if the attribute is required
 * @param type     the type of the attribute
 */
public record DynamicAttributeDto(String name, boolean required, Type type) implements Serializable {
}
