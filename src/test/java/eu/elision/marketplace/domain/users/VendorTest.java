package eu.elision.marketplace.domain.users;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VendorTest {

    @Test
    void getSetLogo() {
        Vendor vendor = new Vendor();
        final String logo = RandomStringUtils.random(4);
        vendor.setLogo(logo);

        assertThat(vendor.getLogo()).isEqualTo(logo);
    }

    @Test
    void getSetTheme() {
        Vendor vendor = new Vendor();
        final String theme = RandomStringUtils.random(4);
        vendor.setTheme(theme);

        assertThat(vendor.getTheme()).isEqualTo(theme);
    }

    @Test
    void getSetIntroduction() {
        Vendor vendor = new Vendor();
        final String introduction = RandomStringUtils.random(4);
        vendor.setIntroduction(introduction);

        assertThat(vendor.getIntroduction()).isEqualTo(introduction);
    }

    @Test
    void getSetVatNumber() {
        Vendor vendor = new Vendor();
        final String vatNumber = RandomStringUtils.random(4);
        vendor.setVatNumber(vatNumber);

        assertThat(vendor.getVatNumber()).isEqualTo(vatNumber);
    }
}