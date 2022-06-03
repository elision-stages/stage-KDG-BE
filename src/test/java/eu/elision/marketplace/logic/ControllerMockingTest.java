package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.logic.services.algolia.AlgoliaIndexerService;
import eu.elision.marketplace.logic.services.product.DynamicAttributeService;
import eu.elision.marketplace.logic.services.product.DynamicAttributeValueService;
import eu.elision.marketplace.logic.services.product.ProductService;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;
import eu.elision.marketplace.web.dtos.product.EditProductDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.webexceptions.InvalidDataException;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class ControllerMockingTest
{

    @InjectMocks
    Controller controller;
    @Mock
    UserService userService;
    @Mock
    ProductService productService;
    @Mock
    AlgoliaIndexerService algoliaIndexerService;
    @Mock
    DynamicAttributeService dynamicAttributeService;
    @Mock
    DynamicAttributeValueService dynamicAttributeValueService;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void testSaveProductByVendor()
    {
        final Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));

        final ArrayList<String> images = new ArrayList<>();
        final ArrayList<AttributeValue<String, String>> attributes = new ArrayList<>();
        final ArrayList<DynamicAttributeValue<?>> convertedAttribtues = new ArrayList<>();

        final Category category = new Category();
        category.setId(RandomUtils.nextLong());

        Product product = new Product(
                RandomUtils.nextLong(),
                RandomUtils.nextInt(),
                RandomStringUtils.randomAlphabetic(50),
                category,
                vendor,
                RandomStringUtils.randomAlphabetic(50),
                images,
                convertedAttribtues
        );

        ProductDto productDto = new ProductDto(
                0L,
                product.getPrice(),
                product.getDescription(),
                product.getTitle(),
                images,
                category,
                attributes,
                vendor.getId(),
                vendor.getBusinessName()
        );

        when(userService.findVendorByEmail(vendor.getEmail())).thenReturn(vendor);
        when(algoliaIndexerService.indexProduct(product)).thenReturn(product);
        when(dynamicAttributeService.getSavedAttributes(attributes, category.getId())).thenReturn(convertedAttribtues);
        when(productService.save(productDto, convertedAttribtues, vendor)).thenReturn(product);

        Product savedProduct = controller.saveProduct(vendor.getEmail(), productDto, null);

        assertThat(savedProduct).isEqualTo(product);
    }

    @Test
    void testSaveProductWithNullEmail()
    {
        Exception exception = assertThrows(InvalidDataException.class, () -> controller.saveProduct(null, null, null));
        assertThat(exception.getMessage()).isEqualTo("Identification is required");
    }

    @Test
    void testSaveProductWithToken()
    {
        final Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));
        vendor.setToken(RandomStringUtils.randomAlphabetic(50));

        final ArrayList<String> images = new ArrayList<>();
        final ArrayList<AttributeValue<String, String>> attributes = new ArrayList<>();
        final ArrayList<DynamicAttributeValue<?>> convertedAttribtues = new ArrayList<>();

        final Category category = new Category();
        category.setId(RandomUtils.nextLong());

        Product product = new Product(
                RandomUtils.nextLong(),
                RandomUtils.nextInt(),
                RandomStringUtils.randomAlphabetic(50),
                category,
                vendor,
                RandomStringUtils.randomAlphabetic(50),
                images,
                convertedAttribtues
        );

        ProductDto productDto = new ProductDto(
                0L,
                product.getPrice(),
                product.getDescription(),
                product.getTitle(),
                images,
                category,
                attributes,
                vendor.getId(),
                vendor.getBusinessName()
        );

        when(userService.findVendorByEmail(vendor.getEmail())).thenReturn(vendor);
        when(algoliaIndexerService.indexProduct(product)).thenReturn(product);
        when(dynamicAttributeService.getSavedAttributes(attributes, category.getId())).thenReturn(convertedAttribtues);
        when(productService.save(productDto, convertedAttribtues, vendor)).thenReturn(product);
        when(bCryptPasswordEncoder.matches(vendor.getToken(), vendor.getToken())).thenReturn(true);

        Product savedProduct = controller.saveProduct(vendor.getEmail(), productDto, vendor.getToken());

        assertThat(savedProduct).isEqualTo(product);
    }

    @Test
    void testSaveProductWrongApiToken()
    {
        final Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));

        ProductDto productDto = new ProductDto(
                0L,
                RandomUtils.nextInt(),
                RandomStringUtils.randomAlphabetic(50),
                RandomStringUtils.randomAlphabetic(50),
                new ArrayList<>(),
                new Category(),
                new ArrayList<>(),
                vendor.getId(),
                vendor.getBusinessName()
        );

        String randomToken = RandomStringUtils.random(50);

        when(userService.findVendorByEmail(vendor.getEmail())).thenReturn(vendor);
        when(bCryptPasswordEncoder.matches(vendor.getToken(), randomToken)).thenReturn(false);

        final String vendorEmail = vendor.getEmail();
        Exception exception = assertThrows(InvalidDataException.class, () -> controller.saveProduct(vendorEmail, productDto, randomToken));

        assertThat(exception.getMessage()).isEqualTo("Token did not match vendor token");
    }

    @Test
    void testEditProductHappy()
    {
        final Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));

        final ArrayList<String> images = new ArrayList<>();
        final ArrayList<AttributeValue<String, String>> attributes = new ArrayList<>();
        final ArrayList<DynamicAttributeValue<?>> convertedAttribtues = new ArrayList<>();

        final Category category = new Category();
        category.setId(RandomUtils.nextLong());

        Product product = new Product(
                RandomUtils.nextLong(),
                RandomUtils.nextInt(),
                RandomStringUtils.randomAlphabetic(50),
                category,
                vendor,
                RandomStringUtils.randomAlphabetic(50),
                images,
                convertedAttribtues
        );

        EditProductDto editProductDto = new EditProductDto(
                0L,
                category,
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                images,
                attributes
        );
        when(userService.findVendorByEmail(vendor.getEmail())).thenReturn(vendor);
        when(dynamicAttributeService.getSavedAttributes(editProductDto.attributes(), category.getId())).thenReturn(convertedAttribtues);
        when(productService.editProduct(Mapper.toProduct(editProductDto, editProductDto.category(), vendor, convertedAttribtues))).thenReturn(product);

        assertThat(controller.editProduct(editProductDto, vendor.getEmail())).isEqualTo(product);
    }

    @Test
    void testEditProductUserNotFound()
    {
        final Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));

        final ArrayList<String> images = new ArrayList<>();
        final ArrayList<AttributeValue<String, String>> attributes = new ArrayList<>();
        final ArrayList<DynamicAttributeValue<?>> convertedAttribtues = new ArrayList<>();

        final Category category = new Category();
        category.setId(RandomUtils.nextLong());

        Product product = new Product(
                RandomUtils.nextLong(),
                RandomUtils.nextInt(),
                RandomStringUtils.randomAlphabetic(50),
                category,
                vendor,
                RandomStringUtils.randomAlphabetic(50),
                images,
                convertedAttribtues
        );
        EditProductDto editProductDto = new EditProductDto(
                0L,
                category,
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                images,
                attributes
        );

        when(userService.findUserByEmail(vendor.getEmail())).thenReturn(null);

        final String vendorEmail = vendor.getEmail();
        Exception exception = assertThrows(NotFoundException.class, () -> controller.editProduct(editProductDto, vendorEmail));

        assertThat(exception.getMessage()).isEqualTo(String.format("User with email %s not found", vendor.getEmail()));
    }

    @Test
    void testEditProductUserNotVendor()
    {
        final Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));

        final ArrayList<String> images = new ArrayList<>();
        final ArrayList<AttributeValue<String, String>> attributes = new ArrayList<>();
        final ArrayList<DynamicAttributeValue<?>> convertedAttribtues = new ArrayList<>();

        final Category category = new Category();
        category.setId(RandomUtils.nextLong());

        Product product = new Product(
                RandomUtils.nextLong(),
                RandomUtils.nextInt(),
                RandomStringUtils.randomAlphabetic(50),
                category,
                vendor,
                RandomStringUtils.randomAlphabetic(50),
                images,
                convertedAttribtues
        );
        EditProductDto editProductDto = new EditProductDto(
                0L,
                category,
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                images,
                attributes
        );

        when(userService.findUserByEmail(vendor.getEmail())).thenReturn(new Customer());

        final String vendorEmail = vendor.getEmail();
        Exception exception = assertThrows(NotFoundException.class, () -> controller.editProduct(editProductDto, vendorEmail));

        assertThat(exception.getMessage()).isEqualTo(String.format("User with email %s is not a vendor", vendor.getEmail()));

    }

}