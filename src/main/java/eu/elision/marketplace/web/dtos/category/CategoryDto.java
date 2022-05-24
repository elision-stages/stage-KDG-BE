package eu.elision.marketplace.web.dtos.category;

import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;

import java.io.Serializable;
import java.util.Collection;

/**
 * Dto object for categories
 *
 * @param id              the id of the category
 * @param name            the name of the category
 * @param parentId        the id of the parent category
 * @param characteristics the characteristics of the category
 */
public record CategoryDto(Long id, String name, Long parentId,
                          Collection<DynamicAttributeDto> characteristics) implements Serializable {
}
