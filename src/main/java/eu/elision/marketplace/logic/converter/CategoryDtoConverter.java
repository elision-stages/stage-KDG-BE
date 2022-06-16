package eu.elision.marketplace.logic.converter;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.converter.populator.Populator;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Coverter to convert category to category dto
 */
@Component
public class CategoryDtoConverter extends Converter<Category, CategoryDto> {
    /**
     * Constructor which takes the populators to later convert the objects with
     *
     * @param populators A list of populators
     */
    public CategoryDtoConverter(List<Populator<Category, CategoryDto>> populators) {
        super(populators, CategoryDto.class);
    }
}
