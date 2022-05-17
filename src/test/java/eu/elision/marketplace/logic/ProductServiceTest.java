package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.logic.services.users.ProductService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void editProduct() {
        final int initSize = productService.findAllProducts().size();

        Product product = new Product();
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
}