package eu.elision.marketplace.domain.product;

import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeBoolValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeDoubleValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeIntValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeStringValue;
import eu.elision.marketplace.domain.users.Vendor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

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
        product.getAttributes().add(new DynamicAttributeStringValue(RandomUtils.nextLong(1, 100), "enum", enumValue));


        assertThat(product.getAttributes().get(0).getAttributeName()).isEqualTo("bool");
        assertThat((boolean) product.getAttributes().get(0).getValue()).isEqualTo(bool);

        assertThat(product.getAttributes().get(1).getAttributeName()).isEqualTo("int");
        assertThat(product.getAttributes().get(1).getValue()).isEqualTo(intValue);

        assertThat(product.getAttributes().get(2).getAttributeName()).isEqualTo("double");
        assertThat(product.getAttributes().get(2).getValue()).isEqualTo(doubleValue);

        assertThat(product.getAttributes().get(3).getAttributeName()).isEqualTo("enum");
        assertThat(product.getAttributes().get(3).getValue()).isEqualTo(enumValue);
    }
}