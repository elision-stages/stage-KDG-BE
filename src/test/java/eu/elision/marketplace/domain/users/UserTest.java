package eu.elision.marketplace.domain.users;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getName() {
        User vendor = new Vendor();
        vendor.setName("vendor");

        assertThat(vendor.getName()).isEqualTo("vendor");
    }

    @Test
    void getEmail() {
        User vendor = new Vendor();
        vendor.setEmail("vendor");

        assertThat(vendor.getEmail()).isEqualTo("vendor");
    }

    @Test
    void getPassword() {
        User vendor = new Vendor();
        vendor.setPassword("vendor");

        assertThat(vendor.getPassword()).isEqualTo("vendor");
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
        vendor.setValidated(true);

        assertThat(vendor.isValidated()).isTrue();
    }
}