package eu.elision.marketplace.web.dtos;

import eu.elision.marketplace.domain.product.category.attributes.Type;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryMakeDtoTest {

    @Test
    void testGetterSetter() {
        Collection<DynamicAttributeDto> characteristics = new ArrayList<>();
        characteristics.add(new DynamicAttributeDto(RandomStringUtils.randomAlphabetic(5), RandomUtils.nextBoolean(), Type.DECIMAL, new ArrayList<>()));
        final String name = RandomStringUtils.randomAlphabetic(5);
        final long parentId = RandomUtils.nextLong();
        CategoryMakeDto categoryMakeDto = new CategoryMakeDto(name, parentId, characteristics);

        assertThat(categoryMakeDto.name()).isEqualTo(name);
        assertThat(categoryMakeDto.parentId()).isEqualTo(parentId);
        assertThat(categoryMakeDto.characteristics()).hasSize(1);
    }
}