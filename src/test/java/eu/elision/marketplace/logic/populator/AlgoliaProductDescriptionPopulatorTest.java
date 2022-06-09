package eu.elision.marketplace.logic.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.logic.populator.algolia.AlgoliaProductDescriptionPopulator;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlgoliaProductDescriptionPopulatorTest {
    @Test
    void Test() {
        Product product = new Product();
        product.setDescription("test desc");

        AlgoliaProductDto algoliaProductDto = new AlgoliaProductDto();

        AlgoliaProductDescriptionPopulator populator = new AlgoliaProductDescriptionPopulator();
        populator.populate(product, algoliaProductDto);

        assertThat(algoliaProductDto.getDescription()).isEqualTo("test desc");
    }
}
