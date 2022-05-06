package eu.elision.marketplace.web.webexceptions;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NotFoundExceptionTest
{
    @Test
    void testConstructor()
    {
        final String message = RandomStringUtils.randomAlphabetic(3);
        NotFoundException notFoundException = new NotFoundException(message);

        assertThat(notFoundException.getMessage()).hasToString(message);
    }
}