package eu.elision.marketplace.domain.users;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {

    @Test
    void getFirstName() {
        User vendor = new Vendor();
        final String name = RandomStringUtils.random(4);
        vendor.setFirstName(name);

        assertThat(vendor.getFirstName()).isEqualTo(name);
    }

    @Test
    void getLastName() {
        User vendor = new Vendor();
        final String name = RandomStringUtils.random(4);
        vendor.setLastName(name);

        assertThat(vendor.getLastName()).isEqualTo(name);
    }

    @Test
    void getEmail() {
        User vendor = new Vendor();
        final String email = RandomStringUtils.random(4);
        vendor.setEmail(email);

        assertThat(vendor.getEmail()).isEqualTo(email);
    }

    @Test
    void getPassword() {
        User vendor = new Vendor();
        final String password = RandomStringUtils.random(4);
        vendor.setPassword(password);

        assertThat(vendor.getPassword()).isEqualTo(password);
    }

    @Test
    void getCreatedDate() {
        User vendor = new Vendor();
        LocalDateTime now = LocalDateTime.now();
        vendor.setCreatedDate(now);

        assertThat(vendor.getCreatedDate()).isEqualTo(now);
    }

    @Test
    void isValidated() {
        User vendor = new Vendor();
        final boolean validated = RandomUtils.nextInt(0,2) == 1;
        vendor.setValidated(validated);

        assertThat(vendor.isValidated()).isEqualTo(validated);
    }
}