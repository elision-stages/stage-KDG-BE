package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.repositories.UserRepository;
import eu.elision.marketplace.web.dtos.AddressDto;
import eu.elision.marketplace.web.dtos.CustomerDto;
import eu.elision.marketplace.web.dtos.VendorDto;
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
        return userRepository.findById(id).orElse(null);
    }

    public Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();

        customer.setFirstName(customerDto.firstName());
        customer.setLastName(customerDto.lastName());
        customer.setEmail(customerDto.email());
        customer.setPassword(customerDto.password());

        return customer;
    }

    public AddressDto toAddressDto(Address address)
    {
        return address == null ? null : new AddressDto(address.getStreet(), address.getNumber(), address.getPostalCode(), address.getCity());
    }

    public CustomerDto toCustomerDto(Customer customer)
    {
        return new CustomerDto(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());
    }

    public void save(VendorDto vendorDto)
    {
        userRepository.save(toVendor(vendorDto));
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
        vendor.setVatNumber(vendorDto.vatNumber());
        vendor.setPhoneNumber(vendorDto.phoneNumber());
        vendor.setBusinessName(vendorDto.businessName());
        return vendor;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
