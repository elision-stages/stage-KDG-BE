package eu.elision.marketplace.logic.populator.category;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.populator.Populator;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryNamePopulator implements Populator<CategoryMakeDto, Category>
{
    @Override
    public void populate(CategoryMakeDto source, Category target)
    {
        target.setName(source.name());
    }
}
