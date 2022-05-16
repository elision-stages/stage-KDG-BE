package eu.elision.marketplace.web.dtos.product;

import eu.elision.marketplace.web.dtos.attributes.AttributeValue;

import eu.elision.marketplace.domain.product.category.Category;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public record ProductDto(double price, String description, String title, List<String> images, Category category,
                         Collection<AttributeValue<String, String>> attributes, long vendorId) implements Serializable {
}
