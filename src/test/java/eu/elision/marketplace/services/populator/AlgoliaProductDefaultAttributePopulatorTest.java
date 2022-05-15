package eu.elision.marketplace.services.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlgoliaProductDefaultAttributePopulatorTest {
    @Test
    void Test() {
        Vendor vendor = new Vendor();
        vendor.setBusinessName("Business");
        Product product = new Product();
        product.setTitle("name");
        product.setId(123L);
        product.setVendor(vendor);
        product.setPrice(55.95);

        AlgoliaProductDto algoliaProductDto = new AlgoliaProductDto();

        AlgoliaProductDefaultAttributePopulator populator = new AlgoliaProductDefaultAttributePopulator();
        populator.populate(product, algoliaProductDto);


        assertThat(algoliaProductDto.getObjectID()).isEqualTo(123L);
        assertThat(algoliaProductDto.getPrice()).isEqualTo(55.95);
        assertThat(algoliaProductDto.getVendor()).isEqualTo("Business");
        assertThat(algoliaProductDto.getName()).isEqualTo("name");
    }
}
