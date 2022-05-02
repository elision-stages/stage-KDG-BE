package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ControllerTest {
    @Autowired
    Controller controller;

    @Test
    void saveCostumerWithAddress() {
        final int initUserRepoSize = controller.findAllUsers().size();
        final int initAddressRepoSize = controller.findAllAddresses().size();

        final Customer customer = new Customer();
        final Address address = new Address();

        final String city = RandomStringUtils.randomAlphabetic(5);
        final String number = String.valueOf(RandomUtils.nextInt());
        final String postalCode = String.valueOf(RandomUtils.nextInt());
        final String street = RandomStringUtils.randomAlphabetic(5);

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        address.setPostalCode(postalCode);
        address.setStreet(street);
        address.setNumber(number);
        address.setCity(city);

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setMainAddress(address);
        customer.setPassword(password);

        long addressId = controller.saveAddress(address).getId();
        long customerId = controller.saveUser(customer).getId();

        assertThat(controller.findAllAddresses()).hasSize(1 + initAddressRepoSize);
        assertThat(controller.findAllUsers()).hasSize(1 + initUserRepoSize);

        Address addressFromRepo = controller.findAddressById(addressId);
        Customer customerFromRepo = (Customer) controller.findUserById(customerId);
        assertThat(addressFromRepo).isNotNull();
        assertThat(customerFromRepo).isNotNull();

        assertThat(addressFromRepo.getPostalCode()).hasToString(postalCode);
        assertThat(addressFromRepo.getCity()).hasToString(city);
        assertThat(addressFromRepo.getNumber()).hasToString(number);
        assertThat(addressFromRepo.getStreet()).hasToString(street);

        assertThat(customerFromRepo.getEmail()).hasToString(email);
        assertThat(customerFromRepo.getFirstName()).hasToString(firstName);
        assertThat(customerFromRepo.getLastName()).hasToString(lastName);
    }

}