package eu.elision.marketplace.logic.converter.populator.categorydto;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CategoryDtoNamePopulatorTest {

    @Test
    void populate() {
        Category category = new Category();
        category.setName(RandomStringUtils.randomAlphabetic(50));
        CategoryDto categoryDto = new CategoryDto();

        CategoryDtoNamePopulator categoryDtoNamePopulator = new CategoryDtoNamePopulator();
        categoryDtoNamePopulator.populate(category, categoryDto);

        assertThat(categoryDto.getName()).isEqualTo(category.getName());
    }
}