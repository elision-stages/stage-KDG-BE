package eu.elision.marketplace.logic.helpers;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.UserDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.product.SmallProductDto;
import eu.elision.marketplace.web.dtos.users.VendorDto;
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

    @Test
    void testToSmallDto() {
        final long id = RandomUtils.nextLong(1, 100);
        final String name = RandomStringUtils.randomAlphabetic(5);
        final String image1 = RandomStringUtils.randomAlphabetic(5);
        final Category category = new Category();
        category.setId(RandomUtils.nextLong(1, 100));
        category.setName(RandomStringUtils.randomAlphabetic(5));
        final Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong(1, 100));
        final String description = RandomStringUtils.randomAlphabetic(5);
        final ArrayList<DynamicAttributeValue<?>> attributes = new ArrayList<>();
        final int price = RandomUtils.nextInt(1, 100);
        Product product = new Product(id, price, name, category, vendor, description, List.of(image1, RandomStringUtils.randomAlphabetic(5)), attributes);

        SmallProductDto smallProductDto = Mapper.toSmallProductDto(product);

        assertThat(smallProductDto.id()).isEqualTo(id);
        assertThat(smallProductDto.name()).isEqualTo(name);
        assertThat(smallProductDto.image()).isEqualTo(image1);
        assertThat(smallProductDto.category()).isEqualTo(category.getName());
        assertThat(smallProductDto.description()).isEqualTo(description);
        assertThat(smallProductDto.price()).isEqualTo(price);
    }

    @Test
    void testToVendorDto()
    {
        Vendor vendor = new Vendor();
        vendor.setLogo(RandomStringUtils.randomAlphabetic(50));
        vendor.setTheme(RandomStringUtils.randomAlphabetic(50));
        vendor.setIntroduction(RandomStringUtils.randomAlphabetic(50));
        vendor.setVatNumber(RandomStringUtils.randomAlphabetic(50));
        vendor.setPhoneNumber(RandomStringUtils.randomAlphabetic(50));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(50));
        vendor.setLastName(RandomStringUtils.randomAlphabetic(50));
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setValidated(RandomUtils.nextBoolean());

        final VendorDto vendorDto = Mapper.toVendorDto(vendor);
        assertThat(vendorDto.logo()).isEqualTo(vendor.getLogo());
        assertThat(vendorDto.theme()).isEqualTo(vendor.getTheme());
        assertThat(vendorDto.introduction()).isEqualTo(vendor.getIntroduction());
        assertThat(vendorDto.vatNumber()).isEqualTo(vendor.getVatNumber());
        assertThat(vendorDto.phoneNumber()).isEqualTo(vendor.getPhoneNumber());
        assertThat(vendorDto.businessName()).isEqualTo(vendor.getBusinessName());
        assertThat(vendorDto.firstName()).isEqualTo(vendor.getFirstName());
        assertThat(vendorDto.lastName()).isEqualTo(vendor.getLastName());
        assertThat(vendorDto.email()).isEqualTo(vendor.getEmail());
        assertThat(vendorDto.validated()).isEqualTo(vendor.isValidated());
    }
}