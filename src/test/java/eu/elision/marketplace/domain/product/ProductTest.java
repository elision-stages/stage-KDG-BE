package eu.elision.marketplace.domain.product;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeBoolValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeDoubleValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeEnumValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeIntValue;
import eu.elision.marketplace.domain.users.Vendor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest
{

    @Test
    void getSetPrice()
    {
        Product product = new Product();
        final int price = RandomUtils.nextInt();
        product.setPrice(price);
        assertThat(product.getPrice()).isEqualTo(price);
    }

    @Test
    void getSetVendor()
    {
        Vendor vendor = new Vendor();
        Product product = new Product();
        product.setVendor(vendor);

        assertThat(product.getVendor()).isEqualTo(vendor);
    }

    @Test
    void getSetDescription()
    {
        Product product = new Product();
        final String desc = RandomStringUtils.random(5);
        product.setDescription(desc);

        assertThat(product.getDescription()).isEqualTo(desc);
    }

    @Test
    void getImages()
    {
        Product product = new Product();
        product.getImages().add(RandomStringUtils.random(5));

        assertThat(product.getImages()).hasSize(1);
    }

    @Test
    void getSetAttributes()
    {
        Product product = new Product();

        final boolean bool = RandomUtils.nextInt(0, 2) == 1;
        final int intValue = RandomUtils.nextInt();
        final double doubleValue = RandomUtils.nextDouble();
        final String enumValue = RandomStringUtils.random(4);

        product.getAttributes().add(new DynamicAttributeBoolValue(RandomUtils.nextLong(1, 100), "bool", bool));
        product.getAttributes().add(new DynamicAttributeIntValue(RandomUtils.nextLong(1, 100), "int", intValue));
        product.getAttributes().add(new DynamicAttributeDoubleValue(RandomUtils.nextLong(1, 100), "double", doubleValue));
        product.getAttributes().add(new DynamicAttributeEnumValue(RandomUtils.nextLong(1, 100), "enum", enumValue));


        assertThat(product.getAttributes().get(0).getAttributeName()).isEqualTo("bool");
        assertThat((boolean) product.getAttributes().get(0).getValue()).isEqualTo(bool);

        assertThat(product.getAttributes().get(1).getAttributeName()).isEqualTo("int");
        assertThat(product.getAttributes().get(1).getValue()).isEqualTo(intValue);

        assertThat(product.getAttributes().get(2).getAttributeName()).isEqualTo("double");
        assertThat(product.getAttributes().get(2).getValue()).isEqualTo(doubleValue);

        assertThat(product.getAttributes().get(3).getAttributeName()).isEqualTo("enum");
        assertThat(product.getAttributes().get(3).getValue()).isEqualTo(enumValue);
    }

    @Test
    void testEquals()
    {
        Category category = new Category();
        Vendor vendor = new Vendor();

        final String description = RandomStringUtils.random(4);

        Product product1 = new Product(1L, "", 2, category, vendor, description, new ArrayList<>(), new ArrayList<>());
        Product product2 = new Product(1L, "", 2, category, vendor, description, new ArrayList<>(), new ArrayList<>());

        assertThat(product1.equals(product2)).isTrue();

        product2.setPrice(1);
        assertThat(product1.equals(product2)).isFalse();
        product2.setPrice(2);
        assertThat(product1.equals(product2)).isTrue();

        Vendor vendor1 = new Vendor();
        vendor1.setFirstName(RandomStringUtils.random(4));
        vendor1.setLastName(RandomStringUtils.random(4));
        product2.setVendor(vendor1);
        assertThat(product1.equals(product2)).isFalse();
        product2.setVendor(vendor);
        assertThat(product1.equals(product2)).isTrue();

        product2.setDescription(RandomStringUtils.random(4));
        assertThat(product1.equals(product2)).isFalse();
        product2.setDescription(description);
        assertThat(product1.equals(product2)).isTrue();

        product2.setImages(new ArrayList<>(List.of(RandomStringUtils.random(5))));
        assertThat(product1.equals(product2)).isFalse();
        product2.setImages(new ArrayList<>());
        assertThat(product1.equals(product2)).isTrue();

        product2.setAttributes(new ArrayList<>(List.of(new DynamicAttributeBoolValue(RandomUtils.nextLong(1, 100), RandomStringUtils.random(4), RandomUtils.nextInt(0, 2) == 1))));
        assertThat(product1.equals(product2)).isFalse();
        product2.setAttributes(new ArrayList<>());
        assertThat(product1.equals(product2)).isTrue();

        assertThat(product1.equals(product1)).isTrue();
        assertThat(product1.equals(new Vendor())).isFalse();


    }

    @Test
    void testHashCode()
    {
        Product product = new Product();

        assertThat(product.hashCode()).isNotZero();
    }

    @Test
    void testToString()
    {
        Product product = new Product();
        assertThat(product.toString()).hasToString("Product(id=null, title=null, price=0.0, category=null, vendor=null, description=null, images=[], attributes=[])");
    }
}