package eu.elision.marketplace.domain.users;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AddressTest {

    @Test
    void getSetStreet() {
        Address address = new Address();
        final String street = RandomStringUtils.random(5);
        address.setStreet(street);

        assertThat(address.getStreet()).isEqualTo(street);
    }

    @Test
    void getSetNumber() {
        Address address = new Address();
        final String number = RandomStringUtils.random(5);
        address.setNumber(number);

        assertThat(address.getNumber()).isEqualTo(number);
    }

    @Test
    void getSetPostalCode() {
        Address address = new Address();
        final String postal_code = RandomStringUtils.random(5);
        address.setPostalCode(postal_code);

        assertThat(address.getPostalCode()).isEqualTo(postal_code);
    }

    @Test
    void getSetCity() {
        Address address = new Address();
        final String city = RandomStringUtils.random(5);
        address.setCity(city);

        assertThat(address.getCity()).isEqualTo(city);
    }
}