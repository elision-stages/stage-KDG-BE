package eu.elision.marketplace.logic.services.users;

import eu.elision.marketplace.domain.users.*;
import eu.elision.marketplace.exceptions.InvalidDataException;
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.exceptions.UnauthorisedException;
import eu.elision.marketplace.logic.services.vat.VATService;
import eu.elision.marketplace.repositories.UserRepository;
import lombok.NoArgsConstructor;
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
    private VATService vatService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Public constructor
     *
     * @param userRepository        the user repository that the service needs to use
     * @param validator             the user validator that the service needs to use
     * @param bCryptPasswordEncoder An BCryptPasswordEncoder instance
     */
    public UserService(UserRepository userRepository, Validator validator, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.validator = validator;
        this.vatService = new VATService();
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    //------------------------------------- save / create / update users

    /**
     * Edit a user. Throws an invalid data exception when the user does not exist in the repository
     *
     * @param user the user that needs to be edited
     * @return the edited user
     */
    public User editUser(User user)
    {
        if (user == null) throw new InvalidDataException("User can not be null");
        findUserById(user.getId());
        findUserByEmail(user.getEmail());

        return userRepository.save(user);
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

        if (user.getId() != null) throw new InvalidDataException("New user can't have an id");
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new InvalidDataException(String.format("User with email %s already exists", user.getEmail()));

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty())
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<User> constraintViolation : violations)
                stringBuilder.append(constraintViolation.getMessage());

            throw new ConstraintViolationException("Error occurred: " + stringBuilder, violations);
        }

        if (user instanceof Vendor vendor && vatService.checkVatService(vendor.getVatNumber()) == null)
            throw new InvalidDataException("VAT check failed");

        return userRepository.save(user);
    }

    /**
     * Create an admin account
     *
     * @return the created admin
     */
    public Admin createAdmin()
    {
        final User userById = userRepository.findById(1000L).orElse(null);
        if (userById != null) return (Admin) userById;

        Admin admin = new Admin();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admintest@elision.eu");
        admin.setPassword("$2a$12$l65u2sm3M8b1Wumi0Rht1.IOmnKpby9oKvXUIznJjBVE4D26RQtBa"); // passw0rD

        return userRepository.save(admin);
    }

    /**
     * Update the API token of a vendor
     *
     * @param vendor The vendor
     * @return The new API token (32 char hex)
     */
    public String updateToken(Vendor vendor)
    {
        SecureRandom random = new SecureRandom();
        String token = new BigInteger(130, random).toString(16).substring(0, 32);
        String encToken = bCryptPasswordEncoder.encode(token);
        vendor.setToken(encToken);
        userRepository.save(vendor);
        return token;
    }

    //------------------------------------------------- find users

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
     * A method to get the vendor of a given id. This method checks if the returned user is a vendor and throws a NotFoundException when no user is found or if the user is not a vendor.
     *
     * @param vendorId the id of the vendor
     * @return the vendor
     */
    public Vendor findVendorById(long vendorId)
    {
        User user = findUserById(vendorId);
        if (!(user instanceof Vendor))
            throw new NotFoundException(String.format("User with id %s is not a vendor", vendorId));
        return (Vendor) user;
    }

    /**
     * Find a user by email
     *
     * @param email the email of the user
     * @return the user with given email or null when not found
     */
    public User findUserByEmail(String email)
    {
        final User user = userRepository.findByEmail(email);
        if (user == null) throw new NotFoundException(String.format("User with email %s not found", email));
        return user;
    }

    /**
     * Find a vendor from an email. Throws a not found exception when no user is found and a invalid data exception when given email doesn't belong to a vendor
     *
     * @param vendorEmail the email of the vendor
     * @return the vendor with given email
     */
    public Vendor findVendorByEmail(String vendorEmail)
    {
        User user = findUserByEmail(vendorEmail);
        if (!(user instanceof Vendor vendor)) throw new InvalidDataException("User with given email is not a vendor");
        return vendor;
    }

    /**
     * Find customer with given email
     *
     * @param userEmail the email that you want
     * @return customer with given email
     */
    public Customer findCustomerByEmail(String userEmail)
    {
        User user = findUserByEmail(userEmail);

        if (user instanceof Customer customer) return customer;
        throw new NotFoundException("User with given email is not a customer");
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException
    {
        return userRepository.findByEmail(mail);
    }

    /**
     * Get the cart of a user
     *
     * @param userMail the email of the user
     * @return the cart of the user with given email
     */
    public Cart getUserCart(String userMail)
    {
        final User user = findUserByEmail(userMail);

        if (!(user instanceof Customer customer))
            throw new UnauthorisedException("Only customers have a shopping cart");
        return customer.getCart();
    }
}
