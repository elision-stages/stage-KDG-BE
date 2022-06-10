package eu.elision.marketplace.logic.converter.populator.categorydto;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.converter.DynamicAttributeDtoConverter;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryDtoCharacteristicsPopulatorTest {

    @InjectMocks
    CategoryDtoCharacteristicsPopulator categoryDtoCharacteristicsPopulator;
    @Mock
    DynamicAttributeDtoConverter dynamicAttributeDtoConverter;

    @Test
    void populate() {
        Category category = new Category();
        category.setCharacteristics(new ArrayList<>());

        CategoryDto categoryDto = new CategoryDto();

        final ArrayList<DynamicAttributeDto> returned = new ArrayList<>();
        when(dynamicAttributeDtoConverter.convertAll(category.getCharacteristics())).thenReturn(returned);

        categoryDtoCharacteristicsPopulator.populate(category, categoryDto);
        assertThat(categoryDto.getCharacteristics()).isEqualTo(returned);
    }
}