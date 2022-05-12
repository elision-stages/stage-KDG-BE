package eu.elision.marketplace.web.dtos.product;

import eu.elision.marketplace.web.dtos.attributes.AttributeValue;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public record ProductDto(double price, String description, List<String> images,
                         Collection<AttributeValue<String, String>> attributes, long vendorId) implements Serializable
{
}
