package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.users.Admin;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.exceptions.InvalidDataException;
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.logic.services.vat.VATService;
import eu.elision.marketplace.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
import static org.mockito.ArgumentMatchers.any;
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
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    VATService vatService;

    @Test
    void testSaveUser()
    {
        final Customer customer = new Customer();

        customer.setId(RandomUtils.nextLong());
        customer.setFirstName(RandomStringUtils.randomAlphabetic(50));
        customer.setLastName(RandomStringUtils.randomAlphabetic(50));
        customer.setEmail(RandomStringUtils.randomAlphabetic(50));
        customer.setPassword(RandomStringUtils.randomAlphabetic(50));

        when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(userRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);

        final User userWithId = userService.findUserById(customer.getId());
        assertThat(userWithId).isNotNull();

        assertThat(userWithId.getEmail()).hasToString(customer.getEmail());
        assertThat(userWithId.getFirstName()).hasToString(customer.getFirstName());
        assertThat(userWithId.getLastName()).hasToString(customer.getLastName());
        assertThat(userWithId.getPassword()).hasToString(customer.getPassword());

    }

    @Test
    void saveUserWithViolations()
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

        customer.setId(RandomUtils.nextLong());
        customer.setFirstName(RandomStringUtils.randomAlphabetic(50));
        customer.setLastName(RandomStringUtils.randomAlphabetic(50));
        customer.setEmail(RandomStringUtils.randomAlphabetic(50));
        customer.setPassword(RandomStringUtils.randomAlphabetic(50));

        when(validator.validate(customer)).thenReturn(new HashSet<>());
        when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(userRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);

        Customer edit = new Customer();
        edit.setId(customer.getId());
        edit.setEmail(customer.getEmail());
        edit.setFirstName(RandomStringUtils.randomAlphabetic(6));
        edit.setLastName(RandomStringUtils.randomAlphabetic(6));
        edit.setPassword(String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT)));

        Customer fromRepo = (Customer) userService.editUser(edit);
        assertThat(customer.getFirstName()).isNotEqualTo(fromRepo.getFirstName());
        assertThat(customer.getLastName()).isNotEqualTo(fromRepo.getLastName());
        assertThat(customer.getPassword()).isNotEqualTo(fromRepo.getPassword());
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

        customer.setId(RandomUtils.nextLong());
        customer.setFirstName(RandomStringUtils.randomAlphabetic(50));
        customer.setLastName(RandomStringUtils.randomAlphabetic(50));
        customer.setEmail(RandomStringUtils.randomAlphabetic(50));
        customer.setPassword(RandomStringUtils.randomAlphabetic(50));

        when(userRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        assertThrows(NotFoundException.class, () -> userService.editUser(customer));
    }

    @Test
    void testEditIdNotFound()
    {
        final Customer customer = new Customer();

        customer.setId(RandomUtils.nextLong());
        customer.setFirstName(RandomStringUtils.randomAlphabetic(50));
        customer.setLastName(RandomStringUtils.randomAlphabetic(50));
        customer.setEmail(RandomStringUtils.randomAlphabetic(50));
        customer.setPassword(RandomStringUtils.randomAlphabetic(50));

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

    @Test
    void testEditUserIdNotFound()
    {
        Admin admin = new Admin();
        admin.setId(RandomUtils.nextLong());
        admin.setFirstName(RandomStringUtils.randomAlphabetic(50));
        admin.setLastName(RandomStringUtils.randomAlphabetic(50));
        admin.setEmail(RandomStringUtils.randomAlphabetic(50));
        admin.setValidated(RandomUtils.nextBoolean());

        when(userRepository.findByEmail(admin.getEmail())).thenReturn(admin);
        when(userRepository.findById(admin.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> userService.editUser(admin));

        assertThat(exception.getMessage()).isEqualTo(String.format("User with id %s not found", admin.getId()));
    }

    @Test
    void testSaveNullUser()
    {
        assertThat(userService.save(null)).isNull();
    }

    @Test
    void testSaveUserEmailNotFound()
    {
        Vendor vendor = new Vendor();
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));

        when(userRepository.findByEmail(vendor.getEmail())).thenReturn(vendor);

        Exception exception = assertThrows(InvalidDataException.class, () -> userService.save(vendor));
        assertThat(exception.getMessage()).isEqualTo(String.format("User with email %s already exists", vendor.getEmail()));
    }

    @Test
    void testSaveUserInvalidVat()
    {
        Vendor vendor = new Vendor();
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setVatNumber(RandomStringUtils.randomAlphabetic(10));

        when(userRepository.findById(vendor.getId())).thenReturn(Optional.of(vendor));
        when(userRepository.findByEmail(vendor.getEmail())).thenReturn(null);
        when(validator.validate(vendor)).thenReturn(new HashSet<>());
        when(vatService.checkVatService(vendor.getVatNumber())).thenReturn(null);

        Exception exception = assertThrows(InvalidDataException.class, () -> userService.save(vendor));

        assertThat(exception.getMessage()).isEqualTo("The vat number was incorrect");
    }

    @Test
    void testCreateAdmin()
    {
        Admin admin = new Admin();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admintest@elision.eu");
        admin.setPassword("$2a$12$l65u2sm3M8b1Wumi0Rht1.IOmnKpby9oKvXUIznJjBVE4D26RQtBa");

        when(userRepository.findById(1000L)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(admin);

        assertThat(userService.createAdmin()).isEqualTo(admin);
    }

    @Test
    void TestUpdateToken()
    {
        final Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));

        String newToken = RandomStringUtils.randomAlphabetic(50);

        when(bCryptPasswordEncoder.encode(any())).thenReturn(newToken);
        userService.updateToken(vendor);

        assertThat(vendor.getToken()).isEqualTo(newToken);
    }

    @Test
    void testFindVendor()
    {
        Vendor vendor = new Vendor();
        vendor.setEmail(RandomStringUtils.randomAlphabetic(50));

        when(userRepository.findByEmail(vendor.getEmail())).thenReturn(vendor);

        assertThat(userService.findVendorByEmail(vendor.getEmail())).isEqualTo(vendor);
    }

    @Test
    void testFindVendorByEmailNotVendor()
    {
        Customer customer = new Customer();
        customer.setEmail(RandomStringUtils.randomAlphabetic(50));

        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);

        final String customerEmail = customer.getEmail();
        Exception exception =
                assertThrows(InvalidDataException.class, () -> userService.findVendorByEmail(customerEmail));

        assertThat(exception.getMessage()).isEqualTo("User with given email is not a vendor");
    }
}