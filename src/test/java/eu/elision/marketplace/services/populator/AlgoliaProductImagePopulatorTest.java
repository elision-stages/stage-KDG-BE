package eu.elision.marketplace.services.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AlgoliaProductImagePopulatorTest {
    @Test
    void Test() {
        List<String> images = new ArrayList<>();
        images.add("a");
        images.add("b");
        Product product = new Product();
        product.setImages(images);

        AlgoliaProductDto algoliaProductDto = new AlgoliaProductDto();

        AlgoliaProductImagePopulator populator = new AlgoliaProductImagePopulator();
        populator.populate(product, algoliaProductDto);

        assertThat(algoliaProductDto.getImage()).isEqualTo("a");
    }

    @Test
    void TestEmpty() {
        List<String> images = new ArrayList<>();
        Product product = new Product();
        product.setImages(images);

        AlgoliaProductDto algoliaProductDto = new AlgoliaProductDto();

        AlgoliaProductImagePopulator populator = new AlgoliaProductImagePopulator();
        populator.populate(product, algoliaProductDto);

        assertThat(algoliaProductDto.getImage()).isEmpty();
    }

    @Test
    void TestNull() {
        Product product = new Product();
        product.setImages(null);

        AlgoliaProductDto algoliaProductDto = new AlgoliaProductDto();

        AlgoliaProductImagePopulator populator = new AlgoliaProductImagePopulator();
        populator.populate(product, algoliaProductDto);

        assertThat(algoliaProductDto.getImage()).isEmpty();
    }
}
