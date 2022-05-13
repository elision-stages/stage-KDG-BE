package eu.elision.marketplace.web.dtos;

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
        final String image = RandomStringUtils.randomAlphabetic(5);
        final String description = RandomStringUtils.randomAlphabetic(5);
        final int price = RandomUtils.nextInt(1, 100);
        SmallProductDto smallProductDto = new SmallProductDto(id, name, category, image, description, price);

        assertThat(smallProductDto.category()).isEqualTo(category);
        assertThat(smallProductDto.id()).isEqualTo(id);
        assertThat(smallProductDto.name()).isEqualTo(name);
        assertThat(smallProductDto.category()).isEqualTo(category);
        assertThat(smallProductDto.image()).isEqualTo(image);
        assertThat(smallProductDto.description()).isEqualTo(description);
        assertThat(smallProductDto.price()).isEqualTo(price);
    }

}