package eu.elision.marketplace.logic.helpers;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeIntValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Admin;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.UserDto;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;
import eu.elision.marketplace.web.dtos.product.EditProductDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.dtos.product.SmallProductDto;
import eu.elision.marketplace.web.dtos.users.CustomerDto;
import eu.elision.marketplace.web.dtos.users.VendorDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MapperTest {
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
    void testTokenDto() {
        String token = RandomStringUtils.random(50);
        assertThat(Mapper.toTokenDto(token).token()).isEqualTo(token);
    }

    @Test
    void testVendorToUserDtoTest() {
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
    void testCustomerToUserDtoTest() {
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
    void testAdminToUserDtoTest() {
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
    void toProductDto() {
        final Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));

        final ArrayList<String> images = new ArrayList<>();
        final ArrayList<AttributeValue<String, String>> attributes = new ArrayList<>();
        final ArrayList<DynamicAttributeValue<?>> attributeValues = new ArrayList<>();

        final Category category = new Category();
        category.setId(RandomUtils.nextLong());

        EditProductDto editProductDto = new EditProductDto(RandomUtils.nextLong(), category.getId(), RandomStringUtils.randomAlphabetic(50), RandomUtils.nextInt(), RandomStringUtils.randomAlphabetic(50), images, attributes);

        Product product = Mapper.toProduct(editProductDto, category, vendor, attributeValues);

        assertThat(product.getId()).isEqualTo(editProductDto.id());
        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getDescription()).isEqualTo(editProductDto.description());
        assertThat(product.getPrice()).isEqualTo(editProductDto.price());
        assertThat(product.getTitle()).isEqualTo(editProductDto.title());
        assertThat(product.getImages()).isEqualTo(editProductDto.images());
        assertThat(product.getAttributes()).isEqualTo(attributeValues);
    }

    @Test
    void testToCustomer() {
        CustomerDto customerDto = new CustomerDto(RandomStringUtils.randomAlphabetic(50), RandomStringUtils.randomAlphabetic(50), RandomStringUtils.randomAlphabetic(50), RandomStringUtils.randomAlphabetic(50));

        Customer customer = Mapper.toCustomer(customerDto);

        assertThat(customer.getFirstName()).isEqualTo(customerDto.firstName());
        assertThat(customer.getLastName()).isEqualTo(customerDto.lastName());
        assertThat(customer.getEmail()).isEqualTo(customerDto.email());
        assertThat(customer.getPassword()).isEqualTo(customerDto.password());
    }

    @Test
    void testToProduct() {
        ProductDto productDto = new ProductDto(RandomUtils.nextLong(),
                RandomUtils.nextDouble(),
                RandomStringUtils.randomAlphabetic(50),
                RandomStringUtils.randomAlphabetic(50),
                new ArrayList<>(),
                RandomUtils.nextInt(),
                new ArrayList<>(),
                RandomUtils.nextInt(),
                RandomStringUtils.randomAlphabetic(50));

        Collection<DynamicAttributeValue<?>> attributeValues = List.of(new DynamicAttributeIntValue(RandomStringUtils.randomAlphabetic(50), RandomUtils.nextInt()));
        Vendor vendor = new Vendor();
        Category category = new Category();
        category.setId(productDto.categoryId());

        Product product = Mapper.toProduct(productDto, attributeValues, vendor, category);

        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getTitle()).isEqualTo(productDto.title());
        assertThat(product.getPrice()).isEqualTo(productDto.price());
        assertThat(product.getVendor()).isEqualTo(vendor);
        assertThat(product.getDescription()).isEqualTo(productDto.description());
        assertThat(product.getDescription()).isEqualTo(productDto.description());
        assertThat(product.getImages()).isEqualTo(productDto.images());
        assertThat(product.getAttributes()).contains(attributeValues.stream().findFirst().get());
    }

    @Test
    void testToVendor() {
        VendorDto vendorDto = new VendorDto(
                RandomStringUtils.randomAlphabetic(50),
                RandomStringUtils.randomAlphabetic(50),
                RandomStringUtils.randomAlphabetic(50),
                RandomStringUtils.randomAlphabetic(50),
                RandomUtils.nextBoolean(),
                RandomStringUtils.randomAlphabetic(50),
                RandomStringUtils.randomAlphabetic(50),
                RandomStringUtils.randomAlphabetic(50),
                RandomStringUtils.randomAlphabetic(50),
                RandomStringUtils.randomAlphabetic(50),
                RandomStringUtils.randomAlphabetic(50)
        );
        Vendor vendor = Mapper.toVendor(vendorDto);

        assertThat(vendor.getFirstName()).isEqualTo(vendorDto.firstName());
        assertThat(vendor.getLastName()).isEqualTo(vendorDto.lastName());
        assertThat(vendor.getLogo()).isEqualTo(vendorDto.logo());
        assertThat(vendor.getTheme()).isEqualTo(vendorDto.theme());
        assertThat(vendor.getEmail()).isEqualTo(vendorDto.email());
        assertThat(vendor.getPassword()).isEqualTo(vendorDto.password());
        assertThat(vendor.isValidated()).isEqualTo(vendorDto.validated());
        assertThat(vendor.getVatNumber()).isEqualTo(vendorDto.vatNumber());
        assertThat(vendor.getPhoneNumber()).isEqualTo(vendorDto.phoneNumber());
        assertThat(vendor.getBusinessName()).isEqualTo(vendorDto.businessName());
    }
}