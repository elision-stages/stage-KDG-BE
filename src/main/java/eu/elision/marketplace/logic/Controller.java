package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Admin;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.logic.services.orders.OrderLineService;
import eu.elision.marketplace.logic.services.orders.OrderService;
import eu.elision.marketplace.logic.services.product.CategoryService;
import eu.elision.marketplace.logic.services.product.DynamicAttributeService;
import eu.elision.marketplace.logic.services.product.DynamicAttributeValueService;
import eu.elision.marketplace.logic.services.product.ProductService;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.cart.AddProductToCartDto;
import eu.elision.marketplace.web.dtos.cart.CartDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.order.CustomerOrderDto;
import eu.elision.marketplace.web.dtos.order.OrderDto;
import eu.elision.marketplace.web.dtos.product.EditProductDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.dtos.users.CustomerDto;
import eu.elision.marketplace.web.dtos.users.VendorDto;
import eu.elision.marketplace.web.dtos.users.VendorPageDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import eu.elision.marketplace.web.webexceptions.UnauthorisedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * The controller that relays all of the methods of the services. It connects all of the services together
 */
@Service
public class Controller {
    private final UserService userService;
    private final CategoryService categoryService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProductService productService;
    private final DynamicAttributeService dynamicAttributeService;
    private final DynamicAttributeValueService dynamicAttributeValueService;
    private final OrderService orderService;
    private final OrderLineService orderLineService;
    private static final String USER_NOT_FOUND = "User with email %s not found";

    /**
     * Public constructor with all services
     *
     * @param bCryptPasswordEncoder        password encoder
     * @param userService                  user service
     * @param categoryService              category service
     * @param productService               product service
     * @param dynamicAttributeService      dynamic attribute service
     * @param dynamicAttributeValueService dynamic attribute value service
     * @param orderService                 order service
     * @param orderLineService             order line service
     */
    @Autowired
    public Controller(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, CategoryService categoryService, ProductService productService, DynamicAttributeService dynamicAttributeService, DynamicAttributeValueService dynamicAttributeValueService, OrderService orderService, OrderLineService orderLineService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.productService = productService;
        this.dynamicAttributeService = dynamicAttributeService;
        this.dynamicAttributeValueService = dynamicAttributeValueService;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
    }

    //---------------------------------- Find all - only for testing

    /**
     * get all users from the repository
     *
     * @return a list with users
     */
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    /**
     * get all customers from the repository in dto format
     *
     * @return a list with customer dto
     */
    public List<CustomerDto> findAllCustomerDto() {
        return findAllUsers().stream().filter(Customer.class::isInstance).map(user -> userService.toCustomerDto((Customer) user)).toList();
    }

    /**
     * Find all products of a given vendor
     *
     * @param vendor the vendor
     * @return a list of products of a given vendor
     */
    public Collection<Product> findProductsByVendor(Vendor vendor) {
        return productService.findProductsByVendor(vendor);
    }

    /**
     * Get all products
     *
     * @return a list of products
     */
    public Collection<Product> findAllProducts() {
        return productService.findAllProducts();
    }

    /**
     * Get a product from an id
     *
     * @param id the id of the product
     * @return the product with given id
     */
    public Product findProduct(long id) {
        return productService.findProductById(id);
    }

    //---------------------------------- Find all

    /**
     * Get all categories in dto form
     *
     * @return a list of category dtos
     */
    public Collection<CategoryDto> findAllCategoriesDto() {
        return categoryService.findAllDto();
    }

    /**
     * Save a user
     *
     * @param user the user that needs to be saved
     * @return the saved object
     */
    public User saveUser(User user) {
        return userService.save(user);
    }

    /**
     * Save a customer from a customer dto
     *
     * @param customerDto the customer dto that needs to be saved
     */
    public void saveCustomer(CustomerDto customerDto) {
        String password = bCryptPasswordEncoder.encode(customerDto.password());
        CustomerDto newCustomerDto = new CustomerDto(customerDto.firstName(), customerDto.lastName(), customerDto.email(), password);
        Customer customer = userService.toCustomer(newCustomerDto);
        saveUser(customer);
    }

    /**
     * Save a product from a dto object
     *
     * @param vendor     the vendor that wants to save the product
     * @param productDto the object that needs to be saved
     * @return the created product
     */
    public Product saveProduct(Vendor vendor, ProductDto productDto) {
        final Collection<DynamicAttributeValue<?>> productAttributes = dynamicAttributeService.getSavedAttributes(productDto.attributes(), productDto.category().getId());
        dynamicAttributeValueService.save(productAttributes);
        return productService.save(productDto, productAttributes, vendor);
    }

    /**
     * Save a product
     *
     * @param product the product that needs to be saved
     * @return the saved object
     */
    public Product saveProduct(Product product) {
        return productService.save(product);
    }

    /**
     * Save a dynamic attribute
     *
     * @param dynamicAttributeDto the dynamic attribute in dto object
     * @param category            the category of the dynamic attribute
     */
    public void saveDynamicAttribute(DynamicAttributeDto dynamicAttributeDto, Category category) {
        DynamicAttribute dynamicAttribute = dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto, category);
        dynamicAttribute.setCategory(category);
        dynamicAttributeService.save(dynamicAttribute);
    }

    //--------------------------------- findById

    /**
     * Find an user by id
     *
     * @param id the id of the user
     * @return the user with given id
     */
    public User findUserById(long id) {
        return userService.findUserById(id);
    }

    /**
     * Save a vendor from dto object
     *
     * @param vendorDto the dto object that needs to be saved
     * @return the saved object
     */
    public Vendor saveVendor(VendorDto vendorDto) {
        String password = vendorDto.password() == null ? null : bCryptPasswordEncoder.encode(vendorDto.password());
        VendorDto newVendorDto = new VendorDto(vendorDto.firstName(), vendorDto.lastName(), vendorDto.email(), password, false, vendorDto.logo(), vendorDto.theme(), vendorDto.introduction(), vendorDto.vatNumber(), vendorDto.phoneNumber(), vendorDto.businessName());
        return userService.save(newVendorDto);
    }

    /**
     * Find an user by email
     *
     * @param email the email of the user
     * @return user with given email
     */
    public User findUserByEmail(String email) {
        return userService.findUserByEmail(email);
    }

    /**
     * Save a category from dto object
     *
     * @param categoryMakeDto the dto object that needs to be saved
     * @return the created category
     */
    public Category saveCategory(CategoryMakeDto categoryMakeDto) {
        final Category category = categoryService.save(categoryMakeDto);
        return categoryService.save(category, dynamicAttributeService.toDynamicAttributes(categoryMakeDto.characteristics(), category));
    }

    /**
     * Find a category based on the name
     *
     * @param name the name of the category
     * @return the category with given name
     */
    public Category findCategoryByName(String name) {
        return categoryService.findByName(name);
    }

    /**
     * Get all categories
     *
     * @return a list of categories
     */
    public List<Category> findAllCategories() {
        return categoryService.findAll();
    }

    /**
     * Edit a product. Throws an exception when the user email is not found, the email is not of a vendor
     *
     * @param editProductDto the data of the changed product
     * @param userEmail      the email of the vendor that wants to edit the product.
     * @return the product with edited values
     */
    public Product editProduct(EditProductDto editProductDto, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new NotFoundException(String.format(USER_NOT_FOUND, userEmail));
        }
        if (!(user instanceof Vendor vendor))
            throw new NotFoundException(String.format("User with email %s is not a vendor", userEmail));

        final List<DynamicAttributeValue<?>> attributeValues = dynamicAttributeService.getSavedAttributes(editProductDto.attributes(), editProductDto.category().getId()).stream().toList();
        dynamicAttributeValueService.save(attributeValues);
        return productService.editProduct(Mapper.toProduct(editProductDto, editProductDto.category(), vendor, attributeValues));
    }

    /**
     * Add a product to a cart of a user
     *
     * @param customerEmail the email address of the user
     * @param addProductDto the dto of the product that has to be added
     * @return the cart dto of the user
     */
    public CartDto addProductToCart(String customerEmail, AddProductToCartDto addProductDto) {
        Customer customer = (Customer) userService.findUserByEmail(customerEmail);
        customer.getCart().addProduct(productService.findProductById(addProductDto.productId()), addProductDto.count(), addProductDto.add());
        userService.editUser(customer);
        customer = (Customer) userService.findUserByEmail(customerEmail);

        return Mapper.toCartDto(customer.getCart());
    }

    /**
     * Delete a product - <strong>Only for testing</strong>
     *
     * @param productId the id of the product that needs to be deleted
     */
    public void deleteProduct(long productId) {
        productService.delete(productId);
    }

    /**
     * Delete a product from a user
     *
     * @param productId the id of the product that needs to be deleted
     * @param userEmail the email of the vendor that wants to delete a product
     */
    public void deleteProduct(long productId, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) throw new NotFoundException(String.format(USER_NOT_FOUND, userEmail));
        if (!(user instanceof Vendor vendor))
            throw new NotFoundException(String.format("User with id %s is not a vendor", user.getId()));

        Product product = productService.findProductById(productId);
        if (!(Objects.equals(product.getVendor().getId(), vendor.getId())))
            throw new UnauthorisedException(String.format("Vendor with id %s is not allowed to delete product with id %s", vendor.getId(), product.getId()));

        productService.delete(productId);
    }

    /**
     * Get the cart in dto form of a customer
     *
     * @param customerName the email of the user
     * @return the cart dto of the user
     */
    public CartDto getCustomerCart(String customerName) {
        final User user = userService.findUserByEmail(customerName);

        if (user == null) throw new NotFoundException("User not found");
        if (!(user instanceof Customer)) throw new UnauthorisedException("Only customers have a shopping cart");
        return Mapper.toCartDto(((Customer) user).getCart());
    }

    /**
     * Checkout the cart of a user
     *
     * @param userEmail the email of the user
     * @return the id of the order. 0 if no order was created
     */
    public long checkoutCart(String userEmail) {
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
    public Order findOrderById(long orderId) {
        return orderService.findOrderById(orderId);
    }

    /**
     * Get all the orders of a given vendor
     *
     * @param userEmail the email of the venodor
     * @return all the vendor orders
     */
    public Collection<OrderDto> getOrders(String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new NotFoundException(String.format(USER_NOT_FOUND, userEmail));
        }

        if (user instanceof Vendor vendor) return orderService.findVendorOrders(vendor);
        if (user instanceof Customer customer) return orderService.findCustomerOrders(customer);
        if (user instanceof Admin) return orderService.findAdminOrders();
        throw new UnauthorisedException(String.format("User with email %s is not a vendor, customer or admin", userEmail));
    }

    /**
     * Save an order
     *
     * @param order the order you want to save
     * @return the saved order with id
     */
    public Order saveOrder(Order order) {
        return orderService.save(order);
    }

    /**
     * Save an order line
     *
     * @param orderLine the order line you want to save
     */
    public void saveOrderLine(OrderLine orderLine) {
        orderLineService.save(orderLine);
    }

    /**
     * Get the order details with the orderlines
     *
     * @param mail The mailaddress of the user asking for the order details
     * @param id   THe ID of the order you need info of
     * @return CustomerOrderDto
     */
    public CustomerOrderDto getOrder(String mail, long id) {
        User user = userService.findUserByEmail(mail);
        return orderService.getOrder(user, id);
    }

    /**
     * Edit a category
     *
     * @param editCategoryDto the dto with the data to edit the category
     */
    public void editCategory(CategoryDto editCategoryDto) {
        categoryService.editCategory(editCategoryDto, dynamicAttributeService.renewAttributes(editCategoryDto, categoryService.findById(editCategoryDto.id())));
    }

    /**
     * Retrieves a specific category by ID
     *
     * @param id The ID to search for
     * @return The requested category
     */
    public Category getCategory(long id)
    {
        return categoryService.getCategory(id);
    }

    /**
     * Get vendor info by id
     *
     * @param id the id of the vendor
     * @return a vendor info dto object with the values of given vendor
     */
    public VendorPageDto getVendorById(long id)
    {
        return Mapper.toVendorPageDto(userService.findVendorById(id), productService.getProductsByVendorId(id));
    }
}
