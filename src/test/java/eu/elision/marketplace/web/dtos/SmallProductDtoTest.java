package eu.elision.marketplace.web.dtos;

import eu.elision.marketplace.web.dtos.product.SmallProductDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SmallProductDtoTest {

    @Test
    void testDto() {
        final long id = RandomUtils.nextLong();
        final String name = RandomStringUtils.randomAlphabetic(5);
        final String category = RandomStringUtils.randomAlphabetic(5);
        final long categoryId = RandomUtils.nextLong();
        final String image = RandomStringUtils.randomAlphabetic(5);
        final String description = RandomStringUtils.randomAlphabetic(5);
        final int price = RandomUtils.nextInt(1, 100);
        final long vendorId = RandomUtils.nextLong();
        final String vendorName = RandomStringUtils.randomAlphabetic(5);

        SmallProductDto smallProductDto = new SmallProductDto(id, name, category, categoryId, image, description, price, vendorId, vendorName);

        assertThat(smallProductDto.category()).isEqualTo(category);
        assertThat(smallProductDto.id()).isEqualTo(id);
        assertThat(smallProductDto.name()).isEqualTo(name);
        assertThat(smallProductDto.category()).isEqualTo(category);
        assertThat(smallProductDto.categoryId()).isEqualTo(categoryId);
        assertThat(smallProductDto.image()).isEqualTo(image);
        assertThat(smallProductDto.description()).isEqualTo(description);
        assertThat(smallProductDto.price()).isEqualTo(price);
        assertThat(smallProductDto.vendorId()).isEqualTo(vendorId);
        assertThat(smallProductDto.vendorName()).isEqualTo(vendorName);
    }

}