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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Controller {
    private final AddressService addressService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public Controller(AddressService addressService, UserService userService, CategoryService categoryService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.addressService = addressService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //---------------------------------- Find all - only for testing
    public List<Address> findAllAddresses() {
        return addressService.findAll();
    }

    public List<User> findAllUsers() {
        return userService.findAllUsers();
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
        String password = bCryptPasswordEncoder.encode(customerDto.password());
        CustomerDto newCustomerDto = new CustomerDto(customerDto.firstName(), customerDto.lastName(), customerDto.email(), password);
        Customer customer = userService.toCustomer(newCustomerDto);
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
        String password = vendorDto.password() == null ? null : bCryptPasswordEncoder.encode(vendorDto.password());
        VendorDto newVendorDto = new VendorDto(
                vendorDto.firstName(),
                vendorDto.lastName(),
                vendorDto.email(),
                password,
                false,
                vendorDto.logo(),
                vendorDto.theme(),
                vendorDto.introduction(),
                vendorDto.vatNumber(),
                vendorDto.phoneNumber(),
                vendorDto.businessName()
        );
        userService.save(newVendorDto);
    }

    public User findUserByEmail(String email)
    {
        return userService.findUserByEmail(email);
    }


    public void saveCategory(CategoryMakeDto categoryMakeDto)
    {
        categoryService.save(Mapper.toCategory(categoryMakeDto), categoryMakeDto.parentId());
    }

    public Category findCategoryByName(String name)
    {
        return categoryService.findByName(name);
    }
}
