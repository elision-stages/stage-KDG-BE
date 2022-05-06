package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.services.helpers.Mapper;
import eu.elision.marketplace.web.dtos.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.CustomerDto;
import eu.elision.marketplace.web.dtos.VendorDto;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class Controller {
    private final AddressService addressService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final DynamicAttributeService dynamicAttributeService;
    private final DynamicAttributeValueService dynamicAttributeValueService;
    private final PickListItemService pickListItemService;
    private final PickListService pickListService;
    private final CategoryService categoryService;

    @Autowired
    public Controller(AddressService addressService, UserService userService, CategoryService categoryService) {
        this.addressService = addressService;
        this.userService = userService;
        this.productService = productService;
        this.dynamicAttributeService = dynamicAttributeService;
        this.dynamicAttributeValueService = dynamicAttributeValueService;
        this.pickListItemService = pickListItemService;
        this.pickListService = pickListService;
        this.categoryService = categoryService;
    }

    //---------------------------------- Find all - only for testing
    public List<Address> findAllAddresses()
    {
        return addressService.findAll();
    }

    public List<User> findAllUsers()
    {
        return userService.findAllUsers();
    }

    public List<CustomerDto> findAllCustomerDto()
    {
        return findAllUsers().stream()
                .filter(Customer.class::isInstance)
                .map(user -> userService.toCustomerDto((Customer) user))
                .toList();
    }

    public Collection<Product> findAllProducts()
    {
        return productService.findAllProducts();
    }

    //---------------------------------- Find all

    public Collection<CategoryDto> findAllCategoriesDto()
    {
        return categoryService.findAllDto();
    }

    //--------------------------------- Save

    public Address saveAddress(Address address)
    {
        return addressService.save(address);
    }

    public User saveUser(User user)
    {
        return userService.save(user);
    }

    public void saveCustomer(CustomerDto customerDto) {
        Customer customer = userService.toCustomer(customerDto);
        if (customer.getMainAddress() != null) {
            saveAddress(customer.getMainAddress());
        }
        saveUser(customer);
    }

    public void saveProduct(ProductDto productDto)
    {
        final Collection<DynamicAttributeValue<?>> productAttributes = dynamicAttributeService.getSavedAttributes(productDto.attributes());
        final User vendor = userService.findUserById(productDto.vendorId());
        dynamicAttributeValueService.save(productAttributes);
        productService.save(productDto, productAttributes, vendor);
    }

    public DynamicAttribute saveDynamicAttribute(DynamicAttributeDto dynamicAttributeDto)
    {
        DynamicAttribute dynamicAttribute = dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto);
        if (dynamicAttribute.getType() == Type.ENUMERATION)
        {
            pickListItemService.save(dynamicAttribute.getEnumList().getItems());
            pickListService.save(dynamicAttribute.getEnumList());
        }
        return dynamicAttributeService.save(dynamicAttribute);
    }

    public void saveCategory(CategoryMakeDto categoryMakeDto)
    {
        final List<DynamicAttribute> dynamicAttributes = categoryMakeDto.characteristics().stream().map(this::saveDynamicAttribute).toList();
        categoryService.save(categoryMakeDto, dynamicAttributes);
    }

    //--------------------------------- findById

    public Address findAddressById(long id)
    {
        return addressService.findById(id);
    }

    public User findUserById(long id)
    {
        return userService.findUserById(id);
    }

    public Vendor saveVendor(VendorDto vendorDto)
    {
        return userService.save(vendorDto);
    }

    public User findUserByEmailAndPassword(String email, String password)
    {
        return userService.findUserByEmailAndPassword(email, password);
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
