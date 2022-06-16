package eu.elision.marketplace.logic.converter.populator.categorydto;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.converter.populator.Populator;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import org.springframework.stereotype.Component;

/**
 * Populator for name field of category dto
 */
@Component
public class CategoryDtoNamePopulator implements Populator<Category, CategoryDto>
{
    @Override
    public void populate(Category source, CategoryDto target) {
        target.setName(source.getName());
    }
}
