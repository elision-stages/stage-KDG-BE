package eu.elision.marketplace.logic.services.users;

import eu.elision.marketplace.domain.users.Admin;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.repositories.UserRepository;
import eu.elision.marketplace.web.api.vat.VATClient;
import eu.elision.marketplace.web.dtos.users.CustomerDto;
import eu.elision.marketplace.web.dtos.users.VendorDto;
import eu.elision.marketplace.web.webexceptions.InvalidDataException;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Set;

/**
 * Service for users
 */
@Service
@NoArgsConstructor
public class UserService implements UserDetailsService
{
    private UserRepository userRepository;
    private Validator validator;
    private VATClient vatClient;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Public constructor
     *
     * @param userRepository the user repository that the service needs to use
     * @param validator      the user validator that the service needs to use
     */
    @Autowired
    public UserService(UserRepository userRepository, Validator validator, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.vatClient = new VATClient();
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /**
     * Edit a user. Throws an invalid data exception when the user does not exist in the repository
     *
     * @param user the user that needs to be edited
     */
    public void editUser(User user)
    {
        if (user == null)
            throw new InvalidDataException("User can not be null");
        if (findUserByEmail(user.getEmail()) == null)
            throw new InvalidDataException(String.format("User with email %s does not exist", user.getEmail()));
        if (findUserById(user.getId()) == null)
            throw new InvalidDataException(String.format("User with id %s does not exist", user.getId()));

        userRepository.save(user);
    }

    /**
     * Find all users - <strong>Only for testing</strong>
     *
     * @return a list of all the users in the repository
     */
    public List<User> findAllUsers()
    {
        return userRepository.findAll();
    }


    /**
     * Save a <strong>new</strong> user. Throws an invalid data exception when the user with email already exists
     *
     * @param user the user that needs to be saved
     * @return The saved object of the user
     */
    public User save(User user)
    {
        if (user == null) return null;
        if (findUserByEmail(user.getEmail()) != null)
            throw new InvalidDataException("An account with this e-mail exists already");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<User> constraintViolation : violations)
                stringBuilder.append(constraintViolation.getMessage());

            throw new ConstraintViolationException("Error occurred: " + stringBuilder, violations);
        }

        if (user instanceof Vendor vendor && vatClient.checkVatService(vendor.getVatNumber()) == null)
            throw new InvalidDataException("VAT check failed");

        return userRepository.save(user);
    }

    /**
     * Find a user by id
     *
     * @param id the id of the user you want to find
     * @return the user with given id
     */
    public User findUserById(long id)
    {
        final User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new NotFoundException(String.format("User with id %s not found", id));
        return user;
    }

    /**
     * Convert a customer dto object to a customer object
     *
     * @param customerDto the customer dto object that needs to be converted
     * @return the customer object with the data from the dto
     */
    public Customer toCustomer(CustomerDto customerDto)
    {
        Customer customer = new Customer();

        customer.setFirstName(customerDto.firstName());
        customer.setLastName(customerDto.lastName());
        customer.setEmail(customerDto.email());
        customer.setPassword(customerDto.password());

        return customer;
    }

    /**
     * Convert a customer object to a dto object
     *
     * @param customer the customer that needs to be converted
     * @return the customer dto object with the data from the dto object
     */
    public CustomerDto toCustomerDto(Customer customer)
    {
        return new CustomerDto(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());
    }

    /**
     * Save a <strong>new</strong> vendor from a dto object. Throws an invalid data exception when email already exists
     *
     * @param vendorDto the vendor dto object with the data of the vendor
     * @return the saved vendor with id
     */
    public Vendor save(VendorDto vendorDto) {
        if (findUserByEmail(vendorDto.email()) != null)
            throw new InvalidDataException("An account with this e-mail exists already");
        return (Vendor) this.save(toVendor(vendorDto));
    }

    private Vendor toVendor(VendorDto vendorDto)
    {
        Vendor vendor = new Vendor();
        vendor.setIntroduction(vendorDto.introduction());
        vendor.setLogo(vendorDto.logo());
        vendor.setTheme(vendorDto.theme());
        vendor.setEmail(vendorDto.email());
        vendor.setFirstName(vendorDto.firstName());
        vendor.setLastName(vendorDto.lastName());
        vendor.setPassword(vendorDto.password());
        vendor.setValidated(vendorDto.validated());
        vendor.setVatNumber(vendorDto.vatNumber());
        vendor.setPhoneNumber(vendorDto.phoneNumber());
        vendor.setBusinessName(vendorDto.businessName());
        return vendor;
    }

    /**
     * Find a user by email
     *
     * @param email the email of the user
     * @return the user with given email or null when not found
     */
    public User findUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException
    {
        return userRepository.findByEmail(mail);
    }

    /**
     * A method to get the vendor of a given id. This method checks if the returned user is a vendor and throws a NotFoundException when no user is found or if the user is not a vendor.
     *
     * @param vendorId the id of the vendor
     * @return the vendor
     */
    public Vendor findVendorById(long vendorId)
    {
        User user = userRepository.findById(vendorId).orElse(null);
        if (user == null) throw new NotFoundException(String.format("User with id %s not found", vendorId));
        if (!(user instanceof Vendor))
            throw new NotFoundException(String.format("User with id %s is not a vendor", vendorId));
        return (Vendor) user;
    }

    /**
     * Find a vendor by email. Will check if the email belongs to a vendor.
     *
     * @param vendorEmail the email of the vendor
     * @return the vendor from the repository
     */
    public Vendor findVendorByEmail(String vendorEmail)
    {
        User user = findUserByEmail(vendorEmail);
        if (user == null) throw new NotFoundException(String.format("User with email %s not found", vendorEmail));
        if (!(user instanceof Vendor vendor))
            throw new NotFoundException(String.format("Vendor with email %s not found", vendorEmail));

        return vendor;
    }

    /**
     * Create an admin account
     */
    public void createAdmin() {
        Admin admin = (Admin) userRepository.findById(1000L).orElse(new Admin());
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admintest@elision.eu");
        admin.setPassword("$2a$12$l65u2sm3M8b1Wumi0Rht1.IOmnKpby9oKvXUIznJjBVE4D26RQtBa"); // passw0rD

        userRepository.save(admin);
    }

    public String updateToken(Vendor vendor) {
        SecureRandom random = new SecureRandom();
        String token = new BigInteger(130, random).toString(16).substring(0, 32);
        String encToken = bCryptPasswordEncoder.encode(token);
        vendor.setToken(encToken);
        userRepository.save(vendor);
        return token;
    }
}
