package eu.elision.marketplace.web.dtos.product;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;

import java.util.Collection;
import java.util.List;

public record EditProductDto(long id, Category category, String title, double price, String description,
                             List<String> images,
                             Collection<AttributeValue<String, String>> attributes) {
}
