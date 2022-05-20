package eu.elision.marketplace.web.webexceptions;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidDataExceptionTest
{
    @Test
    void testExeption()
    {
        final String message = RandomStringUtils.randomAlphabetic(5);
        InvalidDataException invalidDataException = new InvalidDataException(message);

        assertThat(invalidDataException.getMessage()).isEqualTo(message);
    }
}