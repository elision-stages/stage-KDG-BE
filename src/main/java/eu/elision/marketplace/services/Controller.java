package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.web.dtos.CustomerDto;
import eu.elision.marketplace.web.dtos.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.ProductDto;
import eu.elision.marketplace.web.dtos.VendorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class Controller {
    private final AddressService addressService;
    private final UserService userService;
    private final ProductService productService;
    private final DynamicAttributeService dynamicAttributeService;
    private final DynamicAttributeValueService dynamicAttributeValueService;
    private final PickListItemService pickListItemService;
    private final PickListService pickListService;

    @Autowired
    public Controller(AddressService addressService, UserService userService, ProductService productService, DynamicAttributeService dynamicAttributeService, DynamicAttributeValueService dynamicAttributeValueService, PickListItemService pickListItemService, PickListService pickListService) {
        this.addressService = addressService;
        this.userService = userService;
        this.productService = productService;
        this.dynamicAttributeService = dynamicAttributeService;
        this.dynamicAttributeValueService = dynamicAttributeValueService;
        this.pickListItemService = pickListItemService;
        this.pickListService = pickListService;
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

    public Collection<Product> findAllProducts() {
        return productService.findAllProducts();
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
        saveAddress(customer.getMainAddress());
        saveUser(customer);
    }

    public void saveProduct(ProductDto productDto) {
        final Collection<DynamicAttributeValue<?>> productAttributes = dynamicAttributeService.getSavedAttributes(productDto.attributes());
        final User vendor = userService.findUserById(productDto.vendorId());
        dynamicAttributeValueService.save(productAttributes);
        productService.save(productDto, productAttributes, vendor);
    }

    public void saveDynamicAttribute(DynamicAttributeDto dynamicAttributeDto) {
        DynamicAttribute dynamicAttribute = dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto);
        if (dynamicAttribute.getType() == Type.ENUMERATION) {
            pickListItemService.save(dynamicAttribute.getEnumList().getItems());
            pickListService.save(dynamicAttribute.getEnumList());
        }
        dynamicAttributeService.save(dynamicAttribute);
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
}
