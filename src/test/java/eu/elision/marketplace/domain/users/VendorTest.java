package eu.elision.marketplace.domain.users;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VendorTest {

    @Test
    void getSetLogo() {
        Vendor vendor = new Vendor();
        vendor.setLogo("test");

        assertThat(vendor.getLogo()).isEqualTo("test");
    }

    @Test
    void getSetTheme() {
        Vendor vendor = new Vendor();
        vendor.setTheme("test");

        assertThat(vendor.getTheme()).isEqualTo("test");
    }

    @Test
    void getSetIntroduction() {
        Vendor vendor = new Vendor();
        vendor.setIntroduction("test");

        assertThat(vendor.getIntroduction()).isEqualTo("test");
    }

    @Test
    void getSetVatNumber() {
        Vendor vendor = new Vendor();
        vendor.setVatNumber("test");

        assertThat(vendor.getVatNumber()).isEqualTo("test");
    }
}