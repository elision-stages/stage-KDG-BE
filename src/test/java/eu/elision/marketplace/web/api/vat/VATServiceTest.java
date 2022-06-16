package eu.elision.marketplace.web.api.vat;

import eu.elision.marketplace.logic.services.vat.Business;
import eu.elision.marketplace.logic.services.vat.VATService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VATServiceTest
{

    @Test
    void checkVatService()
    {
        VATService vatService = new VATService();

        final String countryCode = "be";
        final String number = "0458402105";
        Business business = vatService.checkVatService(countryCode, number);

        assertThat(business).isNotNull();
    }

    @Test
    void checkVatServiceFullNumber()
    {
        VATService vatService = new VATService();

        Business business = vatService.checkVatService("BE0458402105");

        assertThat(business).isNotNull();
    }

    @Test
    void checkVatServiceFail()
    {
        VATService vatService = new VATService();
        assertThat(vatService.checkVatService(
                RandomStringUtils.randomAlphabetic(2), RandomStringUtils.randomAlphabetic(10)))
                .isNull();
    }
}