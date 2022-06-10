package eu.elision.marketplace.logic.converter.populator.categorydto;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CategoryDtoParentPopulatorTest {

    @Test
    void populate() {
        Category category = new Category();
        Category parent = new Category();
        parent.setId(RandomUtils.nextLong());
        category.setParent(parent);

        CategoryDto categoryDto = new CategoryDto();

        CategoryDtoParentPopulator categoryDtoParentPopulator = new CategoryDtoParentPopulator();
        categoryDtoParentPopulator.populate(category, categoryDto);

        assertThat(categoryDto.getParentId()).isEqualTo(parent.getId());
    }
}