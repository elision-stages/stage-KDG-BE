package eu.elision.marketplace.web.dtos.category;

import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;

import java.io.Serializable;
import java.util.Collection;

/**
 * Dto to make a new category
 *
 * @param name
 * @param parentId
 * @param characteristics
 */
public record CategoryMakeDto(String name, long parentId,
                              Collection<DynamicAttributeDto> characteristics) implements Serializable
{
}
