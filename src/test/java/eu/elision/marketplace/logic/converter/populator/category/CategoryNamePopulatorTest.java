package eu.elision.marketplace.logic.converter.populator.category;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CategoryNamePopulatorTest {

    @Test
    void populate() {
        CategoryMakeDto categoryDto = new CategoryMakeDto(RandomStringUtils.randomAlphabetic(50), RandomUtils.nextLong(), new ArrayList<>());

        Category category = new Category();
        category.setName(RandomStringUtils.randomAlphabetic(50));

        CategoryNamePopulator categoryNamePopulator = new CategoryNamePopulator();
        categoryNamePopulator.populate(categoryDto, category);

        assertThat(category.getName()).isEqualTo(categoryDto.name());
    }
}