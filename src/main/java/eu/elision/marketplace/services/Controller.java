package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.services.helpers.Mapper;
import eu.elision.marketplace.web.dtos.*;
import eu.elision.marketplace.web.dtos.cart.AddProductToCartDto;
import eu.elision.marketplace.web.dtos.cart.CartDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * The controller that relays all of the methods of the services. It connects all of the services together
 */
@Service
public class Controller {
    private final AddressService addressService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProductService productService;
    private final DynamicAttributeService dynamicAttributeService;
    private final DynamicAttributeValueService dynamicAttributeValueService;
    private final PickListItemService pickListItemService;
    private final PickListService pickListService;
    private final OrderService orderService;

    /**
     * Public constructor with all services
     *
     * @param bCryptPasswordEncoder        password encoder
     * @param addressService               address service
     * @param userService                  user service
     * @param categoryService              category service
     * @param productService               product service
     * @param dynamicAttributeService      dynamic attribute service
     * @param dynamicAttributeValueService dynamic attribute value service
     * @param pickListItemService          pick list item service
     * @param pickListService              pick list service
     * @param orderService                 order service
     */
    @Autowired
    public Controller(BCryptPasswordEncoder bCryptPasswordEncoder, AddressService addressService, UserService userService, CategoryService categoryService, ProductService productService, DynamicAttributeService dynamicAttributeService, DynamicAttributeValueService dynamicAttributeValueService, PickListItemService pickListItemService, PickListService pickListService, OrderService orderService)
    {
        this.addressService = addressService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.productService = productService;
        this.dynamicAttributeService = dynamicAttributeService;
        this.dynamicAttributeValueService = dynamicAttributeValueService;
        this.pickListItemService = pickListItemService;
        this.pickListService = pickListService;
        this.orderService = orderService;
    }

    //---------------------------------- Find all - only for testing

    /**
     * get all addresses from the repository
     *
     * @return a list with addresses
     */
    public List<Address> findAllAddresses()
    {
        return addressService.findAll();
    }

    /**
     * get all users from the repository
     *
     * @return a list with users
     */
    public List<User> findAllUsers()
    {
        return userService.findAllUsers();
    }

    /**
     * get all customers from the repository in dto format
     *
     * @return a list with customer dto
     */
    public List<CustomerDto> findAllCustomerDto()
    {
        return findAllUsers().stream()
                .filter(Customer.class::isInstance)
                .map(user -> userService.toCustomerDto((Customer) user))
                .toList();
    }

    /**
     * Find all products of a given vendor
     *
     * @param vendor the vendor
     * @return a list of products of a given vendor
     */
    public Collection<Product> findProductsByVendor(Vendor vendor)
    {
        return productService.findProductsByVendor(vendor);
    }

    /**
     * Get all products
     *
     * @return a list of products
     */
    public Collection<Product> findAllProducts()
    {
        return productService.findAllProducts();
    }

    /**
     * Get a product from an id
     *
     * @param id the id of the product
     * @return the product with given id
     */
    public Product findProduct(long id)
    {
        return productService.findProductById(id);
    }

    //---------------------------------- Find all

    /**
     * Get all categories in dto form
     *
     * @return a list of category dtos
     */
    public Collection<CategoryDto> findAllCategoriesDto()
    {
        return categoryService.findAllDto();
    }

    //--------------------------------- Save

    /**
     * Save an address
     *
     * @param address the address that needs to be saved
     * @return the saved address
     */
    public Address saveAddress(Address address)
    {
        return addressService.save(address);
    }

    /**
     * Save a user
     *
     * @param user the user that needs to be saved
     * @return the saved object
     */
    public User saveUser(User user)
    {
        return userService.save(user);
    }

    /**
     * Save a customer from a customer dto
     *
     * @param customerDto the customer dto that needs to be saved
     */
    public void saveCustomer(CustomerDto customerDto)
    {
        String password = bCryptPasswordEncoder.encode(customerDto.password());
        CustomerDto newCustomerDto = new CustomerDto(customerDto.firstName(), customerDto.lastName(), customerDto.email(), password);
        Customer customer = userService.toCustomer(newCustomerDto);
        if (customer.getMainAddress() != null)
        {
            saveAddress(customer.getMainAddress());
        }
        saveUser(customer);
    }

    /**
     * Save a product from a dto object
     *
     * @param productDto the object that needs to be saved
     */
    public void saveProduct(ProductDto productDto)
    {
        final Collection<DynamicAttributeValue<?>> productAttributes = dynamicAttributeService.getSavedAttributes(productDto.attributes());
        dynamicAttributeValueService.save(productAttributes);
        productService.save(productDto, productAttributes, vendor);
    }

    /**
     * Save a product
     *
     * @param product the product that needs to be saved
     * @return the saved object
     */
    public Product saveProduct(Product product)
    {
        userService.save(product.getVendor());
        return productService.save(product);
    }

    /**
     * Save a dynamic attribute
     *
     * @param dynamicAttributeDto the dynamic attribute in dto object
     * @param category            the category of the dynamic attribute
     */
    public void saveDynamicAttribute(DynamicAttributeDto dynamicAttributeDto, Category category)
    {
        DynamicAttribute dynamicAttribute = dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto);
        dynamicAttribute.setCategory(category);
        if (dynamicAttribute.getType() == Type.ENUMERATION)
        {
            pickListItemService.save(dynamicAttribute.getEnumList().getItems());
            pickListService.save(dynamicAttribute.getEnumList());
        }
        dynamicAttributeService.save(dynamicAttribute);
    }

    //--------------------------------- findById

    /**
     * Find an address by id
     *
     * @param id the id of the address
     * @return the address with given id
     */
    public Address findAddressById(long id)
    {
        return addressService.findById(id);
    }

    /**
     * Find an user by id
     *
     * @param id the id of the user
     * @return the user with given id
     */
    public User findUserById(long id)
    {
        return userService.findUserById(id);
    }

    /**
     * Save a vendor from dto object
     *
     * @param vendorDto the dto object that needs to be saved
     * @return the saved object
     */
    public Vendor saveVendor(VendorDto vendorDto)
    {
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
        return userService.save(newVendorDto);
    }

    /**
     * Find an user by email
     *
     * @param email the email of the user
     * @return user with given email
     */
    public User findUserByEmail(String email)
    {
        return userService.findUserByEmail(email);
    }

    /**
     * Save a category from dto object
     *
     * @param categoryMakeDto the dto object that needs to be saved
     */
    public void saveCategory(CategoryMakeDto categoryMakeDto)
    {
        categoryService.save(categoryMakeDto);
    }

    /**
     * Find a category based on the name
     *
     * @param name the name of the category
     * @return the category with given name
     */
    public Category findCategoryByName(String name)
    {
        return categoryService.findByName(name);
    }

    /**
     * Get all categories
     *
     * @return a list of categories
     */
    public List<Category> findAllCategories()
    {
        return categoryService.findAll();
    }

    /**
     * Add a product to a cart of a user
     *
     * @param customerEmail the email address of the user
     * @param addProductDto the dto of the product that has to be added
     * @return the cart dto of the user
     */
    public CartDto addProductToCart(String customerEmail, AddProductToCartDto addProductDto)
    {
        Customer customer = (Customer) userService.findUserByEmail(customerEmail);
        customer.getCart().addProduct(productService.findProductById(addProductDto.productId()), addProductDto.count(), addProductDto.add());
        userService.save(customer);
        return Mapper.toCartDto(customer.getCart());
    }

    /**
     * Get the cart in dto form of a customer
     *
     * @param customerName the email of the user
     * @return the cart dto of the user
     */
    public CartDto getCustomerCart(String customerName)
    {
        return Mapper.toCartDto(((Customer) userService.loadUserByUsername(customerName)).getCart());
    }

    /**
     * Checkout the cart of a user
     *
     * @param userEmail the email of the user
     * @return the id of the order. 0 if no order was created
     */
    public long checkoutCart(String userEmail)
    {
        User user = userService.findUserByEmail(userEmail);

        if (!(user instanceof Customer customer))
            throw new NotFoundException(String.format("User with email %s is not a customer", userEmail));

        final Order checkout = customer.getCart().checkout(customer);
        if (checkout == null) return 0L;

        return orderService.save(checkout).getOrderNumber();
    }

    /**
     * Find an order by id
     *
     * @param orderId the id of an order
     * @return the order with the given id
     */
    public Order findOrderById(long orderId)
    {
        return orderService.findOrderById(orderId);
    }
}
