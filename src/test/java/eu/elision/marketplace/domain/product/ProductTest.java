package eu.elision.marketplace.domain.product;

import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeBoolValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeDoubleValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeEnumValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeIntValue;
import eu.elision.marketplace.domain.users.Vendor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void getSetPrice() {
        Product product = new Product();
        product.setPrice(2);
        assertThat(product.getPrice()).isEqualTo(2);
    }

    @Test
    void getSetVendor() {
        Vendor vendor = new Vendor();
        Product product = new Product();
        product.setVendor(vendor);

        assertThat(product.getVendor()).isEqualTo(vendor);
    }

    @Test
    void getSetDescription() {
        Product product = new Product();
        product.setDescription("description");

        assertThat(product.getDescription()).isEqualTo("description");
    }

    @Test
    void getImages() {
        Product product = new Product();
        product.getImages().add("image1");

        assertThat(product.getImages()).hasSize(1);
    }

    @Test
    void getSetAttributes() {
        Product product = new Product();

        product.getAttributes().add(new DynamicAttributeBoolValue("bool", true));
        product.getAttributes().add(new DynamicAttributeIntValue("int", 2));
        product.getAttributes().add(new DynamicAttributeDoubleValue("double", 4.2));
        product.getAttributes().add(new DynamicAttributeEnumValue("enum", "xxl"));


        assertThat(product.getAttributes().get(0).getAttributeName()).isEqualTo("bool");
        assertThat((boolean) product.getAttributes().get(0).getValue()).isTrue();

        assertThat(product.getAttributes().get(1).getAttributeName()).isEqualTo("int");
        assertThat(product.getAttributes().get(1).getValue()).isEqualTo(2);

        assertThat(product.getAttributes().get(2).getAttributeName()).isEqualTo("double");
        assertThat(product.getAttributes().get(2).getValue()).isEqualTo(4.2);

        assertThat(product.getAttributes().get(3).getAttributeName()).isEqualTo("enum");
        assertThat(product.getAttributes().get(3).getValue()).isEqualTo("xxl");
    }

    @Test
    void testEquals() {
        Vendor vendor = new Vendor();

        Product product1 = new Product(2, vendor, "desc", new ArrayList<>(), new ArrayList<>());
        Product product2 = new Product(2, vendor, "desc", new ArrayList<>(), new ArrayList<>());

        assertThat(product1.equals(product2)).isTrue();

        product2.setPrice(1);
        assertThat(product1.equals(product2)).isFalse();
        product2.setPrice(2);
        assertThat(product1.equals(product2)).isTrue();

        Vendor vendor1 = new Vendor();
        vendor1.setName("test");
        product2.setVendor(vendor1);
        assertThat(product1.equals(product2)).isFalse();
        product2.setVendor(vendor);
        assertThat(product1.equals(product2)).isTrue();

        product2.setDescription("test");
        assertThat(product1.equals(product2)).isFalse();
        product2.setDescription("desc");
        assertThat(product1.equals(product2)).isTrue();

        product2.setImages(new ArrayList<>(List.of("image")));
        assertThat(product1.equals(product2)).isFalse();
        product2.setImages(new ArrayList<>());
        assertThat(product1.equals(product2)).isTrue();

        product2.setAttributes(new ArrayList<>(List.of(new DynamicAttributeBoolValue("test", true))));
        assertThat(product1.equals(product2)).isFalse();
        product2.setAttributes(new ArrayList<>());
        assertThat(product1.equals(product2)).isTrue();

    }

    @Test
    void testHashCode() {
        Product product = new Product();

        assertThat(product.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        Product product = new Product();

        assertThat(product.toString()).hasToString("Product(price=0.0, vendor=null, description=null, images=[], attributes=[])");
    }
}