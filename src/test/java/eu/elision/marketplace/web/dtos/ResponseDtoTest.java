package eu.elision.marketplace.web.dtos;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseDtoTest {

    @Test
    void testResponseDto() {
        final String field = RandomStringUtils.randomAlphabetic(5);
        final String value = RandomStringUtils.randomAlphabetic(5);
        ResponseDto responseDto = new ResponseDto(field, value);

        assertThat(responseDto.field()).isEqualTo(field);
        assertThat(responseDto.value()).isEqualTo(value);
    }
}