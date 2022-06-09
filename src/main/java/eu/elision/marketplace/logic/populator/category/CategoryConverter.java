package eu.elision.marketplace.logic.populator.category;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.populator.Converter;
import eu.elision.marketplace.logic.populator.Populator;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryConverter extends Converter<CategoryMakeDto, Category>
{
    protected CategoryConverter(List<Populator<CategoryMakeDto, Category>> populators)
    {
        super(populators, Category.class);
    }
}
