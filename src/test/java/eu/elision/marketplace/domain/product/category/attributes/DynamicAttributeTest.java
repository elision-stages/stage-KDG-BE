package eu.elision.marketplace.domain.product.category.attributes;

import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeBoolValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DynamicAttributeTest {

    @Test
    void getSetId() {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        final long id = RandomUtils.nextLong();
        dynamicAttribute.setId(id);

        assertThat(dynamicAttribute.getId()).isEqualTo(id);
    }

    @Test
    void getSetIdValue() {
        DynamicAttributeValue<Boolean> dynamicAttribute = new DynamicAttributeBoolValue();
        final long id = RandomUtils.nextLong();
        dynamicAttribute.setId(id);

        assertThat(dynamicAttribute.getId()).isEqualTo(id);
    }

}