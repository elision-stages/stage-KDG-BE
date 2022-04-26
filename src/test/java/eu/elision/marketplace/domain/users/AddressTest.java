package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void getSetStreet() {
        Address address = new Address();
        final String street = HelperMethods.randomString(5);
        address.setStreet(street);

        assertThat(address.getStreet()).isEqualTo(street);
    }

    @Test
    void getSetNumber() {
        Address address = new Address();
        final String number = HelperMethods.randomString(5);
        address.setNumber(number);

        assertThat(address.getNumber()).isEqualTo(number);
    }

    @Test
    void getSetPostalCode() {
        Address address = new Address();
        final String postal_code = HelperMethods.randomString(5);
        address.setPostalCode(postal_code);

        assertThat(address.getPostalCode()).isEqualTo(postal_code);
    }

    @Test
    void getSetCity() {
        Address address = new Address();
        final String city = HelperMethods.randomString(5);
        address.setCity(city);

        assertThat(address.getCity()).isEqualTo(city);
    }
}