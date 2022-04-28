package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ControllerTest
{
    @Autowired
    Controller controller;

    @Test
    void saveCostumerWithAddress()
    {
        final Customer customer = new Customer();
        final Address address = new Address();

        final String city = RandomStringUtils.randomAlphabetic(5);
        final String number = String.valueOf(RandomUtils.nextInt());
        final String postalCode = String.valueOf(RandomUtils.nextInt());
        final String street = RandomStringUtils.randomAlphabetic(5);

        final String name = RandomStringUtils.randomAlphabetic(5);
        final String email = RandomStringUtils.randomAlphabetic(10);

        address.setPostalCode(postalCode);
        address.setStreet(street);
        address.setNumber(number);
        address.setCity(city);

        customer.setName(name);
        customer.setEmail(email);
        customer.setMainAddress(address);

        long addressId = controller.saveAddress(address).getId();
        long customerId = controller.saveUser(customer).getId();

        assertThat(controller.findAllAddresses()).hasSize(1);
        assertThat(controller.findAllUsers()).hasSize(1);

        Address addressFromRepo = controller.findAddressById(addressId);
        Customer customerFromRepo = (Customer) controller.findUserById(customerId);
        assertThat(addressFromRepo).isNotNull();
        assertThat(customerFromRepo).isNotNull();

        assertThat(addressFromRepo.getPostalCode()).hasToString(postalCode);
        assertThat(addressFromRepo.getCity()).hasToString(city);
        assertThat(addressFromRepo.getNumber()).hasToString(number);
        assertThat(addressFromRepo.getStreet()).hasToString(street);

        assertThat(customerFromRepo.getEmail()).hasToString(email);
        assertThat(customerFromRepo.getName()).hasToString(name);
    }

}