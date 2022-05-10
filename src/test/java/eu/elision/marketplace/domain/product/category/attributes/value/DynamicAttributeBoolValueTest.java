package eu.elision.marketplace.domain.product.category.attributes.value;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicAttributeBoolValueTest {

    @Test
    void getSetValue() {
        DynamicAttributeBoolValue dynamicAttributeBoolValue = new DynamicAttributeBoolValue();
        final boolean value = RandomUtils.nextBoolean();
        dynamicAttributeBoolValue.setValue(value);

        assertThat(dynamicAttributeBoolValue.getValue()).isEqualTo(value);
    }

    @Test
    void testConstructor() {
        final boolean value = RandomUtils.nextBoolean();
        final String attributeName = RandomStringUtils.randomAlphabetic(5);
        DynamicAttributeBoolValue dynamicAttributeBoolValue = new DynamicAttributeBoolValue(attributeName, value);

        assertThat(dynamicAttributeBoolValue.getValue()).isEqualTo(value);
        assertThat(dynamicAttributeBoolValue.getAttributeName()).isEqualTo(attributeName);
    }
}