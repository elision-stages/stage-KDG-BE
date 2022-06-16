package eu.elision.marketplace.web.api.vat;

import eu.elision.marketplace.logic.services.vat.Business;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessTest
{

    @Test
    void getSetCountryCode()
    {
        Business business = new Business();
        final String countryCode = RandomStringUtils.randomAlphabetic(5);
        business.setCountryCode(countryCode);

        assertThat(business.getCountryCode()).hasToString(countryCode);
    }

    @Test
    void getVatNumber()
    {
        Business business = new Business();
        final String vatNumber = RandomStringUtils.randomAlphabetic(5);
        business.setVatNumber(vatNumber);

        assertThat(business.getVatNumber()).hasToString(vatNumber);
    }

    @Test
    void getName()
    {
        Business business = new Business();
        final String name = RandomStringUtils.randomAlphabetic(5);
        business.setName(name);

        assertThat(business.getName()).hasToString(name);
    }

    @Test
    void getAddress()
    {
        Business business = new Business();
        final String address = RandomStringUtils.randomAlphabetic(5);
        business.setAddress(address);

        assertThat(business.getAddress()).hasToString(address);
    }
}