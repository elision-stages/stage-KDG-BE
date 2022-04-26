package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getName() {
        User vendor = new Vendor();
        final String name = HelperMethods.randomString(4);
        vendor.setName(name);

        assertThat(vendor.getName()).isEqualTo(name);
    }

    @Test
    void getEmail() {
        User vendor = new Vendor();
        final String email = HelperMethods.randomString(4);
        vendor.setEmail(email);

        assertThat(vendor.getEmail()).isEqualTo(email);
    }

    @Test
    void getPassword() {
        User vendor = new Vendor();
        final String password = HelperMethods.randomString(4);
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
        final boolean validated = HelperMethods.randomInt(1) == 1;
        vendor.setValidated(validated);

        assertThat(vendor.isValidated()).isEqualTo(validated);
    }
}