package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Cart;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.exceptions.InvalidDataException;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.logic.services.algolia.AlgoliaIndexerService;
import eu.elision.marketplace.logic.services.orders.OrderService;
import eu.elision.marketplace.logic.services.product.CategoryService;
import eu.elision.marketplace.logic.services.product.DynamicAttributeService;
import eu.elision.marketplace.logic.services.product.DynamicAttributeValueService;
import eu.elision.marketplace.logic.services.product.ProductService;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.logic.services.vat.Business;
import eu.elision.marketplace.logic.services.vat.VATService;
import eu.elision.marketplace.web.dtos.TokenDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * The controller that relays all the methods of the services. It connects all the services together
 */
@Service
public class Controller
{
    private final UserService userService;
    private final CategoryService categoryService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProductService productService;
    private final DynamicAttributeService dynamicAttributeService;
    private final DynamicAttributeValueService dynamicAttributeValueService;
    private final OrderService orderService;
    private final AlgoliaIndexerService algoliaIndexerService;
    private final VATService vatService;

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
     * @param algoliaIndexerService        algolia indexer service
     * @param vatService vat checking service
     */
    @Autowired
    public Controller(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, CategoryService categoryService, ProductService productService, DynamicAttributeService dynamicAttributeService, DynamicAttributeValueService dynamicAttributeValueService, OrderService orderService, AlgoliaIndexerService algoliaIndexerService, VATService vatService)
    {
        this.userService = userService;
        this.categoryService = categoryService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.productService = productService;
        this.dynamicAttributeService = dynamicAttributeService;
        this.dynamicAttributeValueService = dynamicAttributeValueService;
        this.orderService = orderService;
        this.algoliaIndexerService = algoliaIndexerService;
        this.vatService = vatService;
    }

    //---------------------------------- Find all

    /**
     * Get all categories
     *
     * @return a list of categories
     */
    public List<Category> findAllCategories()
    {
        return categoryService.findAll();
    }

    //--------------------------------------------------- Find By
    //---------------------------- Products

    /**
     * Find all products of a given vendor
     *
     * @param vendorEmail the email of the vendor
     * @return a list of products of a given vendor
     */
    public Collection<Product> findProductsByVendor(String vendorEmail)
    {
        return productService.findProductsByVendorEmail(vendorEmail);
    }

    /**
     * Get a product from an id
     *
     * @param id the id of the product
     * @return the product with given id
     */
    public Product findProductById(long id)
    {
        return productService.findProductById(id);
    }

    //---------------------------- Users

    /**
     * Find a user by id
     *
     * @param id the id of the user
     * @return the user with given id
     */
    public User findUserById(long id)
    {
        return userService.findUserById(id);
    }

    /**
     * Find a user by email
     *
     * @param email the email of the user
     * @return user with given email
     */
    public User findUserByEmail(String email)
    {
        return userService.findUserByEmail(email);
    }

    /**
     * Get the cart in dto form of a customer
     *
     * @param userMail the email of the user
     * @return the cart dto of the user
     */
    public CartDto findCustomerCart(String userMail)
    {
        return Mapper.toCartDto(userService.getUserCart(userMail));
    }

    /**
     * Get vendor info by id
     *
     * @param id the id of the vendor
     * @return a vendor info dto object with the values of given vendor
     */
    public VendorPageDto findVendorById(long id)
    {
        return Mapper.toVendorPageDto(userService.findVendorById(id), productService.findProductsByVendorId(id));
    }

    /**
     * Get the token of a vendor
     *
     * @param vendorEmail the email of the vendor
     * @return a dto object of the vendor token
     */
    public TokenDto getVendorToken(String vendorEmail)
    {
        return Mapper.toTokenDto(userService.updateToken(userService.findVendorByEmail(vendorEmail)));
    }

    //---------------------------- Category

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
     * Retrieves a specific category by ID
     *
     * @param id The ID to search for
     * @return The requested category
     */
    public Category findCategoryById(long id)
    {
        return categoryService.findById(id);
    }

    //--------------------------------- Order

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

    /**
     * Get all the orders of a given vendor
     *
     * @param userEmail the email of the vendor
     * @return all the vendor orders
     */
    public Collection<OrderDto> findUserOrders(String userEmail)
    {
        return orderService.findUserOrders(userService.findUserByEmail(userEmail));
    }

    /**
     * Get the order details with the order lines
     *
     * @param mail The email of the user asking for the order details
     * @param id   The ID of the order you need info of
     * @return CustomerOrderDto
     */
    public CustomerOrderDto findOrder(String mail, long id)
    {
        return orderService.getOrder(userService.findUserByEmail(mail), id);
    }

    //----------------------------------------------------------- save

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
     * Save an order
     *
     * @param order the order you want to save
     * @return the saved order with id
     */
    public Order saveOrder(Order order)
    {
        return orderService.save(order);
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
        VendorDto newVendorDto = new VendorDto(vendorDto.firstName(), vendorDto.lastName(), vendorDto.email(), password, false, vendorDto.logo(), vendorDto.theme(), vendorDto.introduction(), vendorDto.vatNumber(), vendorDto.phoneNumber(), vendorDto.businessName());
        return (Vendor) userService.save(Mapper.toVendor(newVendorDto));
    }

    /**
     * Save a category from dto object
     *
     * @param categoryMakeDto the dto object that needs to be saved
     * @return the created category
     */
    public Category saveCategory(CategoryMakeDto categoryMakeDto)
    {
        final Category category = categoryService.save(categoryMakeDto);
        category.setCharacteristics(Mapper.toDynamicAttributes(categoryMakeDto.characteristics(), category));
        return categoryService.save(category);
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
        Customer customer = Mapper.toCustomer(newCustomerDto);
        saveUser(customer);
    }

    /**
     * Save a product from a dto object
     *
     * @param vendor     the vendor that wants to save the product
     * @param productDto the object that needs to be saved
     * @return the created product
     */
    public Product saveProduct(Vendor vendor, @Valid ProductDto productDto)
    {
        final Category category = categoryService.findById(productDto.categoryId());

        final Collection<DynamicAttributeValue<?>> productAttributes = dynamicAttributeService.getSavedAttributes(productDto.attributes(), productDto.categoryId());
        dynamicAttributeValueService.save(productAttributes);
        return productService.save(productDto, productAttributes, vendor, category);
    }

    /**
     * Save a product by vendor email or api call
     *
     * @param vendorEmail the email of the vendor that wants to add a product
     * @param productDto  the information about the product that needs to be added
     * @param apiToken    the api token of the vendor that wants to add a product. Null if the product is not added by api call
     * @return the saved product
     */
    public Product saveProduct(String vendorEmail, ProductDto productDto, String apiToken)
    {
        if (vendorEmail == null)
            throw new InvalidDataException("Identification is required");
        if (apiToken == null)
            return algoliaIndexerService.indexProduct(saveProduct(userService.findVendorByEmail(vendorEmail), productDto));

        Vendor vendor = userService.findVendorByEmail(vendorEmail);
        if (bCryptPasswordEncoder.matches(apiToken, vendor.getToken()))
            return algoliaIndexerService.indexProduct(saveProduct(vendor, productDto));
        throw new InvalidDataException("Token did not match vendor token");
    }

    /**
     * Save a product
     *
     * @param product the product that needs to be saved
     * @return the saved object
     */
    public Product saveProduct(Product product)
    {
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
        dynamicAttributeService.save(Mapper.toDynamicAttribute(dynamicAttributeDto, category));
    }

    //----------------------------------------------------------- Edit

    /**
     * Edit a product. Throws an exception when the user email is not found, the email is not of a vendor
     *
     * @param editProductDto the data of the changed product
     * @param userEmail      the email of the vendor that wants to edit the product.
     * @return the product with edited values
     */
    public Product editProduct(EditProductDto editProductDto, String userEmail)
    {
        final List<DynamicAttributeValue<?>> attributeValues = dynamicAttributeService.getSavedAttributes(editProductDto.attributes(), editProductDto.categoryId()).stream().toList();
        dynamicAttributeValueService.save(attributeValues);
        return algoliaIndexerService.indexProduct(
                productService.editProduct(
                        Mapper.toProduct(editProductDto, categoryService.findById(editProductDto.categoryId()), userService.findVendorByEmail(userEmail), attributeValues)));
    }

    /**
     * Add a product to a cart of a user
     *
     * @param customerEmail the email address of the user
     * @param addProductDto the dto of the product that has to be added
     * @return the cart dto of the user
     */
    public Cart addProductToCart(String customerEmail, AddProductToCartDto addProductDto)
    {
        Customer customer = (Customer) userService.findUserByEmail(customerEmail);
        customer.getCart().addProduct(productService.findProductById(addProductDto.productId()), addProductDto.count(), addProductDto.add());
        userService.editUser(customer);

        return customer.getCart();
    }

    /**
     * Checkout the cart of a user
     *
     * @param userEmail the email of the user
     * @return the id of the order. 0 if no order was created
     */
    public long checkoutCart(String userEmail)
    {
        Customer customer = userService.findCustomerByEmail(userEmail);

        final Order checkout = customer.getCart().checkout(customer);
        if (checkout == null) return 0L;

        return orderService.save(checkout).getOrderNumber();
    }

    /**
     * Edit a category
     *
     * @param editCategoryDto the dto with the data to edit the category
     * @return the edited category
     */
    public Category editCategory(CategoryDto editCategoryDto)
    {
        return categoryService.editCategory(editCategoryDto, dynamicAttributeService.renewAttributes(editCategoryDto, categoryService.findById(editCategoryDto.id())));
    }

    //----------------------------------------------------------- Delete


    /**
     * Delete a product from a user
     *
     * @param productId the id of the product that needs to be deleted
     * @param userEmail the email of the vendor that wants to delete a product
     */
    public void deleteProduct(long productId, String userEmail)
    {
        productService.deleteProduct(productId, userEmail);
    }

    /**
     * Check a vat number
     *
     * @param vat the vat number that needs to be checked
     * @return the business information when everything is ok, invalid data exception otherwise
     */
    public Business checkVat(String vat)
    {
        return vatService.checkVatService(vat);
    }
}
