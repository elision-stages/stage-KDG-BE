package eu.elision.marketplace.logic.converter;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DynamicAttributeDtoConverterTest {

    @Autowired
    Converter<DynamicAttribute, DynamicAttributeDto> converter;

    @Test
    void testConvert() {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        dynamicAttribute.setName(RandomStringUtils.randomAlphabetic(50));
        dynamicAttribute.setRequired(RandomUtils.nextBoolean());
        dynamicAttribute.setType(Type.DECIMAL);

        DynamicAttributeDto dynamicAttributeDto = converter.convert(dynamicAttribute);

        assertThat(dynamicAttributeDto.getName()).isEqualTo(dynamicAttribute.getName());
        assertThat(dynamicAttributeDto.getType()).isEqualTo(dynamicAttribute.getType());
        assertThat(dynamicAttributeDto.isRequired()).isEqualTo(dynamicAttribute.isRequired());

    }

}