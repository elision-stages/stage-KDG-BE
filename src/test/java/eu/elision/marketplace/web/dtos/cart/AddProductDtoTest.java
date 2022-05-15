package eu.elision.marketplace.web.dtos.cart;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddProductToCartDtoTest {

    @Test
    void testFields() {
        final int count = RandomUtils.nextInt();
        final long productId = RandomUtils.nextLong();
        AddProductToCartDto addProductToCartDto = new AddProductToCartDto(productId, count, false);

        assertThat(addProductToCartDto.productId()).isEqualTo(productId);
        assertThat(addProductToCartDto.count()).isEqualTo(count);
    }

}