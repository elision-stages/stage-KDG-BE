package eu.elision.marketplace.logic.populator.categorydto;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.populator.Converter;
import eu.elision.marketplace.logic.populator.Populator;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDtoConverter extends Converter<Category, CategoryDto>
{
    public CategoryDtoConverter(List<Populator<Category, CategoryDto>> populators)
    {
        super(populators, CategoryDto.class);
    }
}
