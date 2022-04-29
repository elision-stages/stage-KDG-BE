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

    @Test
    void getSetPhoneNumber() {
        Vendor vendor = new Vendor();
        final String phoneNumber = RandomStringUtils.random(10, false, true);
        vendor.setPhoneNumber(phoneNumber);

        assertThat(vendor.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void getSetBusinessName() {
        Vendor vendor = new Vendor();
        final String businessName = RandomStringUtils.randomAlphabetic(10);
        vendor.setBusinessName(businessName);

        assertThat(vendor.getBusinessName()).isEqualTo(businessName);
    }
}