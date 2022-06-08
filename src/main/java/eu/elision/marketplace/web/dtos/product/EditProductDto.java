package eu.elision.marketplace.web.dtos.product;

import eu.elision.marketplace.web.dtos.attributes.AttributeValue;

import java.util.Collection;
import java.util.List;

/**
 * Dto to edit a category
 *
 * @param id          the (new) id of the category you want to edit
 * @param title       the (new) title of the product
 * @param price       the (new) price of the product
 * @param description the (new) description of the product
 * @param images      the (new) images of the product
 * @param attributes  the (new) attributes of the product
 */
public record EditProductDto(long id, long categoryId, String title, double price, String description,
                             List<String> images,
                             Collection<AttributeValue<String, String>> attributes)
{
}
