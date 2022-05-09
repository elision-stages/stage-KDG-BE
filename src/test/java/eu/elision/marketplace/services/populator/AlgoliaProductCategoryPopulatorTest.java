package eu.elision.marketplace.services.populator;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Cart;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlgoliaProductCategoryPopulatorTest {
    @Test
    void Test() {
        Category category1 = new Category();
        category1.setName("Kleding");

        Category category2 = new Category();
        category2.setName("Broeken");
        category2.setParent(category1);

        Category category3 = new Category();
        category3.setName("Jeans");
        category3.setParent(category2);

        Product product = new Product();
        product.setCategory(category3);

        AlgoliaProductDto algoliaProductDto = new AlgoliaProductDto();

        AlgoliaProductCategoryPopulator populator = new AlgoliaProductCategoryPopulator();
        populator.populate(product, algoliaProductDto);


        assertThat(algoliaProductDto.getParameters()).hasSize(3);
        assertThat(algoliaProductDto.getParameters()).containsEntry("categories.lvl0", "Kleding");
        assertThat(algoliaProductDto.getParameters()).containsEntry("categories.lvl1", "Kleding > Broeken");
        assertThat(algoliaProductDto.getParameters()).containsEntry("categories.lvl2", "Kleding > Broeken > Jeans");
    }
}