import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TestHelperMethodsTest {

    @Test
    void randomString() {
        String random1, random2;

        random1 = HelperMethods.randomString(10);
        assertThat(random1).hasSize(10);

        random2 = HelperMethods.randomString(10);
        assertThat(random2).hasSize(10);

        assertThat(random1).isNotEqualTo(random2);

    }

    @Test
    void randomInt() {
        int random;

        for (int i = 0; i < 100; i++) {
            random = HelperMethods.randomInt(10);
            assertThat(random >= 0 && random <= 10).isTrue();
        }

        for (int i = 0; i < 100; i++) {
            random = HelperMethods.randomInt();
            assertThat(random >= 0 && random <= 10).isTrue();
        }

    }
}