package eu.elision.marketplace.services.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AlgoliaProductDescriptionPopulatorTest {
    @Test
    void Test() {
        Product product = new Product();
        product.setDescription("test desc");

        AlgoliaProductDto algoliaProductDto = new AlgoliaProductDto();

        AlgoliaProductDefaultAttributePopulator populator = new AlgoliaProductDefaultAttributePopulator();
        populator.populate(product, algoliaProductDto);

        assertThat(algoliaProductDto.getDescription()).isEqualTo("test desc");
    }
}
