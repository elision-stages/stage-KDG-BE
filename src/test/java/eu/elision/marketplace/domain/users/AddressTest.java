package eu.elision.marketplace.domain.users;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void getSetStreet() {
        Address address = new Address();
        address.setStreet("street");

        assertThat(address.getStreet()).isEqualTo("street");
    }

    @Test
    void getSetNumber() {
        Address address = new Address();
        address.setNumber("number");

        assertThat(address.getNumber()).isEqualTo("number");
    }

    @Test
    void getSetPostalCode() {
        Address address = new Address();
        address.setPostalCode("postal code");

        assertThat(address.getPostalCode()).isEqualTo("postal code");
    }

    @Test
    void getSetCity() {
        Address address = new Address();
        address.setCity("city");

        assertThat(address.getCity()).isEqualTo("city");
    }
}