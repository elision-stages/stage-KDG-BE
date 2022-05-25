package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeBoolValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeDoubleValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeIntValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.logic.services.product.CategoryService;
import eu.elision.marketplace.logic.services.product.DynamicAttributeService;
import eu.elision.marketplace.logic.services.product.DynamicAttributeValueService;
import eu.elision.marketplace.logic.services.users.ProductService;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.web.webexceptions.InvalidDataException;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import eu.elision.marketplace.web.webexceptions.UnauthorisedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest
class ProductServiceTest
{

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    DynamicAttributeService dynamicAttributeService;
    @Autowired
    DynamicAttributeValueService dynamicAttributeValueService;

    @Test
    void editProduct()
    {
        final int initSize = productService.findAllProducts().size();

        Product product = new Product();
        product.setCategory(categoryService.save(new Category()));
        final String title = RandomStringUtils.randomAlphabetic(5);
        product.setTitle(title);
        final String description = RandomStringUtils.randomAlphabetic(5);
        product.setDescription(description);

        long productId = productService.save(product).getId();
        assertThat(productService.findAllProducts()).hasSize(initSize + 1);

        product.setTitle(RandomStringUtils.randomAlphabetic(5));
        product.setDescription(RandomStringUtils.randomAlphabetic(5));

        productService.editProduct(product);
        assertThat(productService.findAllProducts()).hasSize(initSize + 1);
        Product fromRepo = productService.findProductById(productId);
        assertThat(fromRepo.getTitle()).isNotEqualTo(title);
        assertThat(fromRepo.getDescription()).isNotEqualTo(description);

    }

    @Test
    void editProductFail()
    {
        Product product = new Product();
        product.setId(RandomUtils.nextLong());
        product.setAttributes(new ArrayList<>());

        Category category = new Category();
        product.setCategory(category);

        assertThrows(NotFoundException.class, () -> productService.editProduct(product));
    }

    @Test
    void editProductFail2()
    {
        Product newProduct = new Product();
        newProduct.setCategory(new Category());
        newProduct.setId(productService.save(new Product()).getId());
        newProduct.setVendor(new Vendor());
        assertThrows(UnauthorisedException.class, () -> productService.editProduct(newProduct));
    }

    @Test
    void editProductWrongCharacteristics()
    {
        Category category = new Category();
        category.setId(categoryService.save(category).getId());

        final DynamicAttribute daInt = new DynamicAttribute();
        daInt.setType(Type.INTEGER);
        daInt.setName(RandomStringUtils.randomAlphabetic(5));
        daInt.setCategory(category);
        daInt.setRequired(true);

        final DynamicAttribute daDouble = new DynamicAttribute();
        daDouble.setType(Type.DECIMAL);
        daDouble.setName(RandomStringUtils.randomAlphabetic(5));
        daDouble.setCategory(category);
        daDouble.setRequired(true);

        final DynamicAttribute daBool = new DynamicAttribute();
        daBool.setType(Type.BOOL);
        daBool.setName(RandomStringUtils.randomAlphabetic(5));
        daBool.setCategory(category);
        daBool.setRequired(true);

        category.setCharacteristics(List.of(dynamicAttributeService.save(daInt), dynamicAttributeService.save(daDouble), dynamicAttributeService.save(daBool)));
        categoryService.save(category);
        // category setup done

        final Vendor vendor = new Vendor();
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(10));
        vendor.setLastName(RandomStringUtils.randomAlphabetic(10));
        vendor.setVatNumber("BE0458402105");
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(10));
        vendor.setPhoneNumber(RandomStringUtils.random(10, false, true));
        vendor.setEmail(String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2)));
        vendor.setPassword(String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT)));


        Product product = new Product();
        product.setPrice(RandomUtils.nextDouble(1, 100));
        product.setVendor((Vendor) userService.save(vendor));
        product.setDescription(RandomStringUtils.randomAlphabetic(100));
        product.setTitle(RandomStringUtils.randomAlphabetic(10));
        product.setCategory(category);
        product.setAttributes(List.of(
                dynamicAttributeValueService.save(new DynamicAttributeIntValue(daInt.getName(), RandomUtils.nextInt())),
                dynamicAttributeValueService.save(new DynamicAttributeDoubleValue(daDouble.getName(), RandomUtils.nextDouble())),
                dynamicAttributeValueService.save(new DynamicAttributeBoolValue(daBool.getName(), RandomUtils.nextBoolean()))
        ));
        product.setId(productService.save(product).getId());
        // base product done

        //verify product repo save
        Product fromRepo = productService.findProductById(product.getId());
        assertThat(fromRepo.getId()).isEqualTo(product.getId());
        assertThat(fromRepo.getTitle()).isEqualTo(product.getTitle());
        assertThat(fromRepo.getPrice()).isEqualTo(product.getPrice());
        assertThat(fromRepo.getCategory().getId()).isEqualTo(product.getCategory().getId());
        assertThat(fromRepo.getVendor().getId()).isEqualTo(product.getVendor().getId());
        assertThat(fromRepo.getDescription()).isEqualTo(product.getDescription());
        assertThat(fromRepo.getImages()).hasSize(product.getImages().size());
        assertThat(fromRepo.getAttributes()).hasSize(product.getAttributes().size());

        ArrayList<DynamicAttributeValue<?>> attributes = new ArrayList<>(product.getAttributes());
        var deletedAttribute = attributes.remove(RandomUtils.nextInt(0, attributes.size()));
        product.setAttributes(attributes);

        Exception exception = assertThrows(InvalidDataException.class, () -> productService.editProduct(product));

        assertThat(exception.getMessage()).contains(deletedAttribute.getAttributeName());
    }


}