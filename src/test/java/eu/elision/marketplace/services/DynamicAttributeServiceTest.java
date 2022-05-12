package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DynamicAttributeServiceTest {

    @Autowired
    private DynamicAttributeService dynamicAttributeService;

    @Test
    void toDynamicAttributeTest() {
        final String name1 = RandomStringUtils.randomAlphabetic(5);
        DynamicAttributeDto dynamicAttributeDto = new DynamicAttributeDto(name1, RandomUtils.nextBoolean(), Type.DECIMAL, null);
        final String name2 = RandomStringUtils.randomAlphabetic(5);
        DynamicAttributeDto dynamicAttributeDto2 = new DynamicAttributeDto(name2, RandomUtils.nextBoolean(), Type.ENUMERATION, new ArrayList<>(List.of(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5))));
        final String name3 = RandomStringUtils.randomAlphabetic(5);
        DynamicAttributeDto dynamicAttributeDto3 = new DynamicAttributeDto(name3, RandomUtils.nextBoolean(), Type.DECIMAL, new ArrayList<>(List.of(RandomStringUtils.randomAlphabetic(4))));

        assertThat(
                dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto).getName()).isEqualTo(name1);
        assertThat(
                dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto2).getName()).isEqualTo(name2);
        assertThat(
                dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto3).getName()).isEqualTo(name3);
    }

}