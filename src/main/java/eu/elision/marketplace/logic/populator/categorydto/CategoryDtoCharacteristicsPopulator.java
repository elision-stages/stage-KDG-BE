package eu.elision.marketplace.logic.populator.categorydto;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.populator.Populator;
import eu.elision.marketplace.logic.populator.dynamicattributedto.DynamicAttributeDtoConverter;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoCharacteristicsPopulator implements Populator<Category, CategoryDto>
{
    final
    DynamicAttributeDtoConverter dynamicAttributeDtoConverter;

    public CategoryDtoCharacteristicsPopulator(DynamicAttributeDtoConverter dynamicAttributeDtoConverter)
    {
        this.dynamicAttributeDtoConverter = dynamicAttributeDtoConverter;
    }

    @Override
    public void populate(Category source, CategoryDto target)
    {
        target.setCharacteristics(dynamicAttributeDtoConverter.convertAll(source.getCharacteristics()));
    }
}
