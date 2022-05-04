package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.repositories.UserRepository;
import eu.elision.marketplace.web.dtos.AddressDto;
import eu.elision.marketplace.web.dtos.CustomerDto;
import eu.elision.marketplace.web.dtos.VendorDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
@NoArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private Validator validator;

    @Autowired
    public UserService(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<User> constraintViolation : violations)
                stringBuilder.append(constraintViolation.getMessage());

            throw new ConstraintViolationException("Error occurred: " + stringBuilder, violations);
        }
        return userRepository.save(user);
    }

    public User findUserById(long id) {
        final User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new NotFoundException(String.format("User with id %s not found", id));
        return user;
    }

    public Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();

        final Address address = new Address();
        address.setCity(customerDto.mainAddress().city());
        address.setStreet(customerDto.mainAddress().street());
        address.setPostalCode(customerDto.mainAddress().postalCode());
        address.setNumber(customerDto.mainAddress().number());

        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());
        customer.setPassword(customerDto.password());
        customer.setValidated(customerDto.validated());
        customer.setMainAddress(address);

        return customer;
    }

    public AddressDto toAddressDto(Address address) {
        return address == null ? null : new AddressDto(address.getStreet(), address.getNumber(), address.getPostalCode(), address.getCity());
    }

    public CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto(customer.getName(), customer.getEmail(), customer.getPassword(), customer.isValidated(), toAddressDto(customer.getMainAddress()));
    }

    public void save(VendorDto vendorDto) {
        userRepository.save(toVendor(vendorDto));
    }

    private Vendor toVendor(VendorDto vendorDto) {
        Vendor vendor = new Vendor();
        vendor.setIntroduction(vendorDto.introduction());
        vendor.setLogo(vendorDto.logo());
        vendor.setTheme(vendorDto.theme());
        vendor.setEmail(vendorDto.email());
        vendor.setName(vendorDto.name());
        vendor.setPassword(vendorDto.password());
        vendor.setValidated(vendorDto.validated());
        vendor.setVatNumber(vendorDto.vatNumber());
        vendor.setPhoneNumber(vendorDto.phoneNumber());
        vendor.setBusinessName(vendorDto.businessName());
        return vendor;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
