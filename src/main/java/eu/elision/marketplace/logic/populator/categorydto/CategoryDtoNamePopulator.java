package eu.elision.marketplace.logic.populator.categorydto;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.populator.Populator;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoNamePopulator implements Populator<Category, CategoryDto>
{
    @Override
    public void populate(Category source, CategoryDto target)
    {
        target.setName(source.getName());
    }
}
