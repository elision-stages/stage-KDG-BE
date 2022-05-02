package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.services.helpers.Mapper;
import eu.elision.marketplace.web.dtos.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.CustomerDto;
import eu.elision.marketplace.web.dtos.VendorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Controller {
    private final AddressService addressService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public Controller(AddressService addressService, UserService userService, CategoryService categoryService) {
        this.addressService = addressService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    //---------------------------------- Find all - only for testing
    public List<Address> findAllAddresses() {
        return addressService.findAll();
    }

    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    public List<CustomerDto> findAllCustomerDto() {
        return findAllUsers().stream()
                .filter(Customer.class::isInstance)
                .map(user -> userService.toCustomerDto((Customer) user))
                .toList();
    }

    //--------------------------------- FindAll
    public List<Category> findAllCategories() {
        return categoryService.findAll();
    }
    //--------------------------------- Save

    public Address saveAddress(Address address) {
        return addressService.save(address);
    }

    public User saveUser(User user) {
        return userService.save(user);
    }

    public void saveCustomer(CustomerDto customerDto) {
        Customer customer = userService.toCustomer(customerDto);
        if (customer.getMainAddress() != null) {
            saveAddress(customer.getMainAddress());
        }
        saveUser(customer);
    }

    //--------------------------------- findById

    public Address findAddressById(long id) {
        return addressService.findById(id);
    }

    public User findUserById(long id) {
        return userService.findUserById(id);
    }

    public void saveVendor(VendorDto vendorDto) {
        userService.save(vendorDto);
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userService.findUserByEmailAndPassword(email, password);
    }


    public void saveCategory(CategoryMakeDto categoryMakeDto) {
        categoryService.save(Mapper.toCategory(categoryMakeDto), categoryMakeDto.parentId());
    }
}
