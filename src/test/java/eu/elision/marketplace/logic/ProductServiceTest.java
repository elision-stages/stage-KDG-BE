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
import eu.elision.marketplace.exceptions.InvalidDataException;
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.exceptions.UnauthorisedException;
import eu.elision.marketplace.logic.services.product.DynamicAttributeValueService;
import eu.elision.marketplace.logic.services.product.ProductService;
import eu.elision.marketplace.repositories.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest
{

    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;
    @Mock
    DynamicAttributeValueService dynamicAttributeValueService;

    @Test
    void editProduct()
    {
        Product product = new Product();
        Category category = new Category();

        product.setId(RandomUtils.nextLong());
        product.setCategory(category);
        product.setTitle(RandomStringUtils.randomAlphabetic(5));
        product.setDescription(RandomStringUtils.randomAlphabetic(5));

        when(productRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product edit = new Product();
        edit.setId(product.getId());
        edit.setTitle(RandomStringUtils.randomAlphabetic(5));
        edit.setDescription(RandomStringUtils.randomAlphabetic(5));
        edit.setCategory(product.getCategory());

        Product fromRepo = productService.editProduct(edit);
        assertThat(fromRepo.getTitle()).isNotEqualTo(product.getTitle());
        assertThat(fromRepo.getDescription()).isNotEqualTo(product.getDescription());

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
        Product product = new Product();
        product.setId(RandomUtils.nextLong());
        final Category category = new Category();
        product.setCategory(category);
        product.setVendor(new Vendor());

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product editProduct = new Product();
        editProduct.setId(product.getId());
        editProduct.setVendor(new Vendor());
        editProduct.setCategory(category);

        assertThrows(UnauthorisedException.class, () -> productService.editProduct(editProduct));
    }

    @Test
    void editProductWrongCharacteristics()
    {
        Category category = new Category();
        category.setId(RandomUtils.nextLong());

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

        category.setCharacteristics(List.of(daInt, daDouble, daBool));
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
        product.setId(RandomUtils.nextLong());
        product.setPrice(RandomUtils.nextDouble(1, 100));
        product.setVendor(vendor);
        product.setDescription(RandomStringUtils.randomAlphabetic(100));
        product.setTitle(RandomStringUtils.randomAlphabetic(10));
        product.setCategory(category);
        product.setAttributes(List.of(
                new DynamicAttributeIntValue(daInt.getName(), RandomUtils.nextInt()),
                new DynamicAttributeDoubleValue(daDouble.getName(), RandomUtils.nextDouble()),
                new DynamicAttributeBoolValue(daBool.getName(), RandomUtils.nextBoolean())
        ));
        // base product done

        ArrayList<DynamicAttributeValue<?>> attributes = new ArrayList<>(product.getAttributes());
        DynamicAttributeValue<?> deletedAttribute = attributes.remove(RandomUtils.nextInt(0, attributes.size()));
        product.setAttributes(attributes);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Exception exception = assertThrows(InvalidDataException.class, () -> productService.editProduct(product));

        assertThat(exception.getMessage()).contains(deletedAttribute.getAttributeName());
    }


}