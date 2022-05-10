package eu.elision.marketplace.services.helpers;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.web.dtos.CategoryDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class MapperTest {
    @Test
    void toCategoryDtoList() {
        Category parent = new Category();
        Category child = new Category();

        String parentName = RandomStringUtils.randomAlphabetic(5);
        String childName = RandomStringUtils.randomAlphabetic(5);

        parent.setName(parentName);
        parent.setId(RandomUtils.nextLong());
        child.setName(childName);
        child.setId(RandomUtils.nextLong());
        parent.getSubCategories().add(child);

        List<CategoryDto> categoryDtos = Mapper.toCategoryDtoList(List.of(parent));

        assertThat(categoryDtos).hasSize(1);
        final CategoryDto parentDto = categoryDtos.get(0);
        assertThat(parentDto).isNotNull();
        assertThat(parentDto.name()).hasToString(parentName);
    }

    @Test
    void categoryInDtoListTest() {
        final Category category = new Category();
        category.setName(RandomStringUtils.randomAlphabetic(4));
        category.setId(RandomUtils.nextLong(1, 10));

        List<CategoryDto> categoryDtos = new ArrayList<>(List.of(Mapper.toCategoryDto(category)));

        assertThat(categoryDtos.stream().anyMatch(categoryDto -> Objects.equals(categoryDto.id(), category.getId()))).isTrue();
    }
}