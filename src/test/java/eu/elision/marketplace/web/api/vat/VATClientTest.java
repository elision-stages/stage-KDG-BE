package eu.elision.marketplace.web.api.vat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VATClientTest
{

    @Test
    void checkVatService()
    {
        VATClient vatClient = new VATClient();

        final String countryCode = "be";
        final String number = "0458402105";
        Business business = vatClient.checkVatService(countryCode, number);

        assertThat(business).isNotNull();
        assertThat(business.getName()).isEqualTo("VZW Karel de Grote Hogeschool, Katholieke Hogeschool Antwerpen");
        assertThat(business.getCountryCode()).isEqualTo(countryCode);
        assertThat(business.getVatNumber()).isEqualTo(number);
        assertThat(business.getAddress()).isEqualTo("Brusselstraat 45\n2018 Antwerpen");
    }

    @Test
    void checkVatServiceFullNumber()
    {
        VATClient vatClient = new VATClient();

        Business business = vatClient.checkVatService("BE0458402105");

        assertThat(business).isNotNull();
        assertThat(business.getName()).isEqualTo("VZW Karel de Grote Hogeschool, Katholieke Hogeschool Antwerpen");
        assertThat(business.getCountryCode()).isEqualTo("BE");
        assertThat(business.getVatNumber()).isEqualTo("0458402105");
        assertThat(business.getAddress()).isEqualTo("Brusselstraat 45\n2018 Antwerpen");
    }

    @Test
    void checkVatServiceFail()
    {
        VATClient vatClient = new VATClient();
        assertThat(vatClient.checkVatService(
                RandomStringUtils.randomAlphabetic(2), RandomStringUtils.randomAlphabetic(10)))
                .isNull();
    }
}