package eu.elision.marketplace.logic.helpers;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Admin;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.UserDto;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;
import eu.elision.marketplace.web.dtos.product.EditProductDto;
import eu.elision.marketplace.web.dtos.product.SmallProductDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MapperTest
{
    @Test
    void toCategoryDtoList()
    {
        Category parent = new Category();
        Category child = new Category();

        String parentName = RandomStringUtils.randomAlphabetic(5);
        String childName = RandomStringUtils.randomAlphabetic(5);

        parent.setName(parentName);
        parent.setId(RandomUtils.nextLong());
        child.setName(childName);
        child.setId(RandomUtils.nextLong());
        parent.getSubCategories().add(child);

//        List<CategoryDto> categoryDtos = Mapper.toCategoryDtoList(List.of(parent));
//
//        assertThat(categoryDtos).hasSize(1);
//        final CategoryDto parentDto = categoryDtos.get(0);
//        assertThat(parentDto).isNotNull();
//        assertThat(parentDto.getName()).hasToString(parentName);
    }


    @Test
    void toUserDtoTest()
    {
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
    void testToSmallDto()
    {
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
    void testTokenDto()
    {
        String token = RandomStringUtils.random(50);
        assertThat(Mapper.toTokenDto(token).token()).isEqualTo(token);
    }

    @Test
    void testVendorToUserDtoTest()
    {
        Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(50));
        vendor.setLastName(RandomStringUtils.randomAlphabetic(50));
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setValidated(RandomUtils.nextBoolean());

        final UserDto userDto = Mapper.toUserDto(vendor);
        assertThat(userDto.email()).isEqualTo(vendor.getEmail());
        assertThat(userDto.firstName()).isEqualTo(vendor.getFirstName());
        assertThat(userDto.lastName()).isEqualTo(vendor.getLastName());
        assertThat(userDto.id()).isEqualTo(vendor.getId());
        assertThat(userDto.role()).isEqualTo("vendor");
    }

    @Test
    void testCustomerToUserDtoTest()
    {
        Customer customer = new Customer();
        customer.setId(RandomUtils.nextLong());
        customer.setFirstName(RandomStringUtils.randomAlphabetic(50));
        customer.setLastName(RandomStringUtils.randomAlphabetic(50));
        customer.setEmail(RandomStringUtils.randomAlphabetic(50));
        customer.setValidated(RandomUtils.nextBoolean());

        final UserDto userDto = Mapper.toUserDto(customer);
        assertThat(userDto.email()).isEqualTo(customer.getEmail());
        assertThat(userDto.firstName()).isEqualTo(customer.getFirstName());
        assertThat(userDto.lastName()).isEqualTo(customer.getLastName());
        assertThat(userDto.id()).isEqualTo(customer.getId());
        assertThat(userDto.role()).isEqualTo("customer");
    }

    @Test
    void testAdminToUserDtoTest()
    {
        Admin admin = new Admin();
        admin.setId(RandomUtils.nextLong());
        admin.setFirstName(RandomStringUtils.randomAlphabetic(50));
        admin.setLastName(RandomStringUtils.randomAlphabetic(50));
        admin.setEmail(RandomStringUtils.randomAlphabetic(50));
        admin.setValidated(RandomUtils.nextBoolean());

        final UserDto userDto = Mapper.toUserDto(admin);
        assertThat(userDto.email()).isEqualTo(admin.getEmail());
        assertThat(userDto.firstName()).isEqualTo(admin.getFirstName());
        assertThat(userDto.lastName()).isEqualTo(admin.getLastName());
        assertThat(userDto.id()).isEqualTo(admin.getId());
        assertThat(userDto.role()).isEqualTo("admin");
    }

    @Test
    void toProductDto()
    {
        final Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));

        final ArrayList<String> images = new ArrayList<>();
        final ArrayList<AttributeValue<String, String>> attributes = new ArrayList<>();
        final ArrayList<DynamicAttributeValue<?>> attributeValues = new ArrayList<>();

        final Category category = new Category();
        category.setId(RandomUtils.nextLong());

        EditProductDto editProductDto = new EditProductDto(
                RandomUtils.nextLong(),
                category.getId(),
                RandomStringUtils.randomAlphabetic(50),
                RandomUtils.nextInt(),
                RandomStringUtils.randomAlphabetic(50),
                images,
                attributes
        );

        Product product = Mapper.toProduct(editProductDto, category, vendor, attributeValues);

        assertThat(product.getId()).isEqualTo(editProductDto.id());
        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getDescription()).isEqualTo(editProductDto.description());
        assertThat(product.getPrice()).isEqualTo(editProductDto.price());
        assertThat(product.getTitle()).isEqualTo(editProductDto.title());
        assertThat(product.getImages()).isEqualTo(editProductDto.images());
        assertThat(product.getAttributes()).isEqualTo(attributeValues);
    }
}