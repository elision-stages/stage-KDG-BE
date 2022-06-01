package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.logic.Controller;
import eu.elision.marketplace.logic.services.product.CategoryService;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.dtos.users.VendorDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest
{
    private static URL base;
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private Integer port;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private Controller controller;

    @BeforeEach
    void setUp() throws MalformedURLException
    {
        restTemplate = new TestRestTemplate();
        base = new URL(String.format("http://localhost:%s", port));
    }

    @Test
    void addProduct()
    {
        final String name = RandomStringUtils.randomAlphabetic(4);
        Category category = categoryService.save(new CategoryMakeDto("Test", 0, new ArrayList<>()));
        controller.saveDynamicAttribute(new DynamicAttributeDto(name, true, Type.INTEGER), category);
        final String vendorName = RandomStringUtils.randomAlphabetic(4);
        final long vendorId = controller.saveVendor(new VendorDto(
                vendorName,
                RandomStringUtils.randomAlphabetic(4),
                String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2)),
                String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT)),
                RandomUtils.nextBoolean(),
                RandomStringUtils.randomAlphabetic(4),
                RandomStringUtils.randomAlphabetic(4),
                RandomStringUtils.randomAlphabetic(50),
                "BE0458402105",
                RandomStringUtils.random(10, false, true),
                RandomStringUtils.randomAlphabetic(4))).getId();

        ArrayList<AttributeValue<String, String>> attributes = new ArrayList<>();
        attributes.add(new AttributeValue<>(name, String.valueOf(RandomUtils.nextInt(1, 100))));

        ResponseEntity<String> response = restTemplate.postForEntity(
                String.format("%s/addProduct", base),
                new ProductDto(RandomUtils.nextInt(), RandomUtils.nextInt(), RandomStringUtils.randomAlphabetic(50), RandomStringUtils.randomAlphabetic(5), new ArrayList<>(), new Category(), attributes, vendorId, vendorName),
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetProductById()
    {
        final Product product = new Product();
        final String description = RandomStringUtils.randomAlphabetic(50);
        final int price = RandomUtils.nextInt();
        final String name = RandomStringUtils.randomAlphabetic(5);

        final Vendor vendor = new Vendor();
        final String firstName = RandomStringUtils.randomAlphabetic(4);
        final String lastName = RandomStringUtils.randomAlphabetic(4);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));
        final String phoneNumber = RandomStringUtils.random(10, false, true);

        product.setDescription(description);
        product.setPrice(price);
        product.setTitle(name);
        //product.setVendor(vendor);

        vendor.setFirstName(firstName);
        vendor.setLastName(lastName);
        vendor.setEmail(email);
        vendor.setPassword(password);
        vendor.setPhoneNumber(phoneNumber);

        long productId = controller.saveProduct(product).getId();
        ResponseEntity<Product> response = restTemplate.getForEntity(String.format("%s/product/%s", base, productId), Product.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo(description);
        assertThat(response.getBody().getPrice()).isEqualTo(price);
        assertThat(response.getBody().getTitle()).isEqualTo(name);
    }
}