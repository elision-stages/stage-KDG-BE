package eu.elision.marketplace.web.dtos;

import eu.elision.marketplace.domain.product.category.attributes.Type;

import java.io.Serializable;
import java.util.Collection;

public record DynamicAttributeDto(String name, boolean required, Type type,
                                  Collection<String> enumValues) implements Serializable {
}
