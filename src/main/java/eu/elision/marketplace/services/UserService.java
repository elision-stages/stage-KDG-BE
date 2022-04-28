package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.repositories.UserRepository;
import eu.elision.marketplace.web.dtos.AddressDto;
import eu.elision.marketplace.web.dtos.CustomerDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class UserService
{
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers()
    {
        return userRepository.findAll();
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public User findUserById(long id)
    {
        return userRepository.findById(id).orElse(null);
    }

    public Customer toCustomer(CustomerDto customerDto){
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

    public AddressDto toAddressDto(Address address){
        return new AddressDto(address.getId(), address.getStreet(), address.getNumber(), address.getPostalCode(), address.getCity());
    }

    public CustomerDto toCustomerDto(Customer customer)
    {
        return new CustomerDto(customer.getName(), customer.getEmail(), customer.getPassword(), customer.isValidated(), toAddressDto(customer.getMainAddress()));
    }
}
