package eu.elision.marketplace.web.dtos.product;

import eu.elision.marketplace.web.dtos.attributes.AttributeValue;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * This is a product DTO used to send products for the shopping cart
 */
public record ProductDto(long id, double price, String description, String title, List<String> images, long categoryId,
                         Collection<AttributeValue<String, String>> attributes, long vendorId,
                         String vendorName) implements Serializable
{
}
