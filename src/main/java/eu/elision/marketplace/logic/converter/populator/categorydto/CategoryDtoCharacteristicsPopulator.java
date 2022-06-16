package eu.elision.marketplace.logic.converter.populator.categorydto;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.converter.DynamicAttributeDtoConverter;
import eu.elision.marketplace.logic.converter.populator.Populator;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import org.springframework.stereotype.Component;

/**
 * Populator to populate the characteristics' field of category dtos
 */
@Component
public class CategoryDtoCharacteristicsPopulator implements Populator<Category, CategoryDto> {
    final
    DynamicAttributeDtoConverter dynamicAttributeDtoConverter;

    /**
     * Public constructor.
     *
     * @param dynamicAttributeDtoConverter the dynamic attribute dto converter that needs to be used
     */
    public CategoryDtoCharacteristicsPopulator(DynamicAttributeDtoConverter dynamicAttributeDtoConverter) {
        this.dynamicAttributeDtoConverter = dynamicAttributeDtoConverter;
    }

    @Override
    public void populate(Category source, CategoryDto target) {
        target.setCharacteristics(dynamicAttributeDtoConverter.convertAll(source.getCharacteristics()));
    }
}
