package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VendorTest {

    @Test
    void getSetLogo() {
        Vendor vendor = new Vendor();
        final String logo = HelperMethods.randomString(4);
        vendor.setLogo(logo);

        assertThat(vendor.getLogo()).isEqualTo(logo);
    }

    @Test
    void getSetTheme() {
        Vendor vendor = new Vendor();
        final String theme = HelperMethods.randomString(4);
        vendor.setTheme(theme);

        assertThat(vendor.getTheme()).isEqualTo(theme);
    }

    @Test
    void getSetIntroduction() {
        Vendor vendor = new Vendor();
        final String introduction = HelperMethods.randomString(4);
        vendor.setIntroduction(introduction);

        assertThat(vendor.getIntroduction()).isEqualTo(introduction);
    }

    @Test
    void getSetVatNumber() {
        Vendor vendor = new Vendor();
        final String vatNumber = HelperMethods.randomString(4);
        vendor.setVatNumber(vatNumber);

        assertThat(vendor.getVatNumber()).isEqualTo(vatNumber);
    }
}