package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class UserServiceTest
{

    @Autowired
    private UserService userService;

    @Test
    void testSaveUser()
    {
        final Customer customer = new Customer();

        final String name = RandomStringUtils.randomAlphabetic(5);
        final String email = RandomStringUtils.randomAlphabetic(10);

        customer.setName(name);
        customer.setEmail(email);

        final long id = userService.save(customer).getId();

        assertThat(userService.findAllUsers()).hasSize(1);

        final User userWithId = userService.findUserById(id);
        assertThat(userWithId).isNotNull();

        assertThat(userWithId.getEmail()).hasToString(email);
        assertThat(userWithId.getName()).hasToString(name);

    }
}