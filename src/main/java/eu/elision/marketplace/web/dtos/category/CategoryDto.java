package eu.elision.marketplace.web.dtos.category;

import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * Dto object for categories
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryDto
{
    private Long id;
    private String name;
    private Long parentId;
    private Collection<DynamicAttributeDto> characteristics;

    /**
     * Public constructor
     *
     * @param id              the getId of the category
     * @param name            the getName of the category
     * @param parentId        the getId of the parent category
     * @param characteristics the characteristics of the category
     */
    public CategoryDto(Long id, String name, Long parentId,
                       Collection<DynamicAttributeDto> characteristics)
    {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.characteristics = characteristics;
    }
}
