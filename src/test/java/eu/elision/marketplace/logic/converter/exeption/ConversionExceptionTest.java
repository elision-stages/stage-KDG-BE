package eu.elision.marketplace.logic.converter.exeption;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConversionExceptionTest {
    @Test
    void testException() {
        final String message = RandomStringUtils.randomAlphabetic(6);
        ConversionException conversionException = new ConversionException(message);

        assertThat(conversionException.getMessage()).isEqualTo(message);
    }

}