package eu.elision.marketplace.web.dtos;

import java.io.Serializable;
import java.util.List;

public record CategoryDto(long id, String name, List<CategoryDto> subcategories) implements Serializable {
}
