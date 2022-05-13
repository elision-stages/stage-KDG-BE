package eu.elision.marketplace.web.dtos;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public record ProductDto(double price, String title, String description, long categoryId, List<String> images,
                         Collection<AttributeValue<String, String>> attributes) implements Serializable {
}
