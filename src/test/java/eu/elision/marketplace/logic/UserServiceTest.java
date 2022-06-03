package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.repositories.UserRepository;
import eu.elision.marketplace.web.webexceptions.InvalidDataException;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserServiceTest
{
    @InjectMocks
    private UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    Validator validator;

    @Test
    void testSaveUser()
    {
        final Customer customer = new Customer();

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        Customer toReturn = new Customer();
        toReturn.setId(RandomUtils.nextLong());
        when(userRepository.save(customer)).thenReturn(toReturn);
        customer.setId(userService.save(customer).getId());

        when(userRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        final User userWithId = userService.findUserById(customer.getId());
        assertThat(userWithId).isNotNull();

        assertThat(userWithId.getEmail()).hasToString(email);
        assertThat(userWithId.getFirstName()).hasToString(firstName);
        assertThat(userWithId.getLastName()).hasToString(lastName);

    }

    @Test
    void saveUserWithVoilations()
    {
        final Customer customer = new Customer();

        Set<ConstraintViolation<Customer>> violations =
                Set.of(new ConstraintViolation<>()
                {
                    @Override
                    public String getMessage()
                    {
                        return null;
                    }

                    @Override
                    public String getMessageTemplate()
                    {
                        return null;
                    }

                    @Override
                    public Customer getRootBean()
                    {
                        return null;
                    }

                    @Override
                    public Class<Customer> getRootBeanClass()
                    {
                        return null;
                    }

                    @Override
                    public Object getLeafBean()
                    {
                        return null;
                    }

                    @Override
                    public Object[] getExecutableParameters()
                    {
                        return new Object[0];
                    }

                    @Override
                    public Object getExecutableReturnValue()
                    {
                        return null;
                    }

                    @Override
                    public Path getPropertyPath()
                    {
                        return null;
                    }

                    @Override
                    public Object getInvalidValue()
                    {
                        return null;
                    }

                    @Override
                    public ConstraintDescriptor<?> getConstraintDescriptor()
                    {
                        return null;
                    }

                    @Override
                    public <U> U unwrap(Class<U> type)
                    {
                        return null;
                    }
                });
        when(validator.validate(customer)).thenReturn(violations);

        Assertions.assertThrows(ConstraintViolationException.class, () -> userService.save(customer));
    }

    @Test
    void testEditUser()
    {
        final Customer customer = new Customer();

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        when(validator.validate(customer)).thenReturn(new HashSet<>());

        Customer toReturn = new Customer();
        toReturn.setId(RandomUtils.nextLong());
        when(userRepository.save(customer)).thenReturn(toReturn);

        customer.setId(userService.save(customer).getId());

        customer.setFirstName(RandomStringUtils.randomAlphabetic(6));
        customer.setLastName(RandomStringUtils.randomAlphabetic(6));
        customer.setPassword(String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT)));

        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);
        when(userRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        userService.editUser(customer);
        Customer fromRepo = (Customer) userService.findUserByEmail(customer.getEmail());

        assertThat(firstName).isNotEqualTo(fromRepo.getFirstName());
        assertThat(lastName).isNotEqualTo(fromRepo.getLastName());
        assertThat(password).isNotEqualTo(fromRepo.getPassword());
    }

    @Test
    void testEditNullUser()
    {
        assertThrows(InvalidDataException.class, () -> userService.editUser(null));
    }

    @Test
    void testEditEmailNotFound()
    {
        final Customer customer = new Customer();

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        assertThrows(InvalidDataException.class, () -> userService.editUser(customer));
    }

    @Test
    void testEditIdNotFound()
    {
        final Customer customer = new Customer();

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        userService.save(customer);
        customer.setId(RandomUtils.nextLong());

        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);
        assertThrows(NotFoundException.class, () -> userService.editUser(customer));
    }

    @Test
    void testFindVendorById()
    {
        Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong(1, 100));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));
        vendor.setPhoneNumber(RandomStringUtils.randomAlphabetic(50));
        vendor.setVatNumber(RandomStringUtils.randomAlphabetic(50));
        vendor.setPassword(RandomStringUtils.randomAlphabetic(50));
        vendor.setLogo(RandomStringUtils.randomAlphabetic(50));
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(50));

        when(userRepository.findById(vendor.getId())).thenReturn(Optional.of(vendor));

        Vendor fromRepo = userService.findVendorById(vendor.getId());
        assertThat(fromRepo).isEqualTo(vendor);
    }

    @Test
    void testFindVendorByIdNotFound()
    {
        Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong(1, 100));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));
        vendor.setPhoneNumber(RandomStringUtils.randomAlphabetic(50));
        vendor.setVatNumber(RandomStringUtils.randomAlphabetic(50));
        vendor.setPassword(RandomStringUtils.randomAlphabetic(50));
        vendor.setLogo(RandomStringUtils.randomAlphabetic(50));
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(50));

        when(userRepository.findById(vendor.getId())).thenReturn(Optional.empty());

        final Long vendorId = vendor.getId();
        Exception exception = assertThrows(NotFoundException.class, () -> userService.findVendorById(vendorId));
        assertThat(exception.getMessage()).isEqualTo(String.format("User with id %s not found", vendorId));
    }

    @Test
    void testFindVendorByIdNotVendor()
    {
        Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong(1, 100));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));
        vendor.setPhoneNumber(RandomStringUtils.randomAlphabetic(50));
        vendor.setVatNumber(RandomStringUtils.randomAlphabetic(50));
        vendor.setPassword(RandomStringUtils.randomAlphabetic(50));
        vendor.setLogo(RandomStringUtils.randomAlphabetic(50));
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(50));

        when(userRepository.findById(vendor.getId())).thenReturn(Optional.of(new Customer()));

        final Long vendorId = vendor.getId();
        Exception exception = assertThrows(NotFoundException.class, () -> userService.findVendorById(vendorId));
        assertThat(exception.getMessage()).isEqualTo(String.format("User with id %s is not a vendor", vendorId));
    }
}