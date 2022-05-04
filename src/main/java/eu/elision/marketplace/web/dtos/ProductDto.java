package eu.elision.marketplace.web.dtos;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public record ProductDto(double price, String description, List<String> images,
                         Collection<Pair<String, String>> attributes, long vendorId) implements Serializable {
}
