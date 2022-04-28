package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Address;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressServiceTest
{
    @Autowired
    AddressService addressService;

    @Test
    void testSave(){
        final Address address = new Address();

        final String city = RandomStringUtils.randomAlphabetic(5);
        final String number = String.valueOf(RandomUtils.nextInt());
        final String postalCode = String.valueOf(RandomUtils.nextInt());
        final String street = RandomStringUtils.randomAlphabetic(5);

        address.setPostalCode(postalCode);
        address.setStreet(street);
        address.setNumber(number);
        address.setCity(city);

        final long id = addressService.save(address).getId();

        assertThat(addressService.findAll()).hasSize(1);
        assertThat(addressService.findById(id)).isNotNull();

        final Address addressFromRepo = addressService.findById(id);
        assertThat(addressFromRepo.getPostalCode()).hasToString(postalCode);
        assertThat(addressFromRepo.getCity()).hasToString(city);
        assertThat(addressFromRepo.getNumber()).hasToString(number);
        assertThat(addressFromRepo.getStreet()).hasToString(street);

    }

}