package eu.elision.marketplace.services.helpers;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.CategoryDto;
import eu.elision.marketplace.web.dtos.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.UserDto;
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

    @Test
    void toCategoryTest() {
        CategoryMakeDto categoryMakeDto = new CategoryMakeDto("Name", 0, new ArrayList<>());
        Category category = Mapper.toCategory(categoryMakeDto);

        assertThat(category.getName()).isEqualTo("Name");
    }

    @Test
    void toUserDtoTest() {
        User user = new Vendor();
        Long id = RandomUtils.nextLong(1, 10);
        String firstName = RandomStringUtils.randomAlphabetic(4);
        String lastName = RandomStringUtils.randomAlphabetic(4);
        String email = RandomStringUtils.randomAlphabetic(4);
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        UserDto userDto = Mapper.toUserDto(user);

        assertThat(userDto.id()).isEqualTo(id);
        assertThat(userDto.firstName()).isEqualTo(firstName);
        assertThat(userDto.lastName()).isEqualTo(lastName);
        assertThat(userDto.email()).isEqualTo(email);
        assertThat(userDto.role()).isEqualTo("vendor");
    }
}