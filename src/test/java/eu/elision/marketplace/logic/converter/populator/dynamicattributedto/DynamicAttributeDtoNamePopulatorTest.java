package eu.elision.marketplace.logic.converter.populator.dynamicattributedto;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DynamicAttributeDtoNamePopulatorTest {

    @Test
    void populate() {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        dynamicAttribute.setName(RandomStringUtils.randomAlphabetic(50));
        DynamicAttributeDto dynamicAttributeDto = new DynamicAttributeDto();

        DynamicAttributeDtoNamePopulator dynamicAttributeDtoNamePopulator = new DynamicAttributeDtoNamePopulator();
        dynamicAttributeDtoNamePopulator.populate(dynamicAttribute, dynamicAttributeDto);

        assertThat(dynamicAttributeDto.getName()).isEqualTo(dynamicAttribute.getName());
    }
}