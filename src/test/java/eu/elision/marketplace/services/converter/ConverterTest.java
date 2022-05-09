package eu.elision.marketplace.services.converter;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConverterTest {
    @Autowired
    Converter<Product, AlgoliaProductDto> algoliaProductConverter;

    @Test
    void test() {
        Vendor vendor = new Vendor();
        vendor.setBusinessName("vendor");
        Product product = new Product();
        product.setId(123L);
        product.setVendor(vendor);
        product.setImages(List.of("test"));
        AlgoliaProductDto result = algoliaProductConverter.convert(product);
        assertThat(result.getImage()).isEqualTo("test");
        assertThat(result.getVendor()).isEqualTo("vendor");
    }

    @Test
    void testBulk() {
        Vendor vendor = new Vendor();
        vendor.setBusinessName("vendor");
        Product product = new Product();
        product.setId(123L);
        product.setVendor(vendor);
        product.setImages(List.of("test"));
        Collection<AlgoliaProductDto> result = algoliaProductConverter.convertAll(List.of(product));
        assertThat(result.size()).isEqualTo(1);
    }
}
