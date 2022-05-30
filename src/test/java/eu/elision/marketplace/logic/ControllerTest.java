package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Admin;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.logic.services.orders.OrderService;
import eu.elision.marketplace.logic.services.product.CategoryService;
import eu.elision.marketplace.logic.services.product.ProductService;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.cart.AddProductToCartDto;
import eu.elision.marketplace.web.dtos.cart.CartDto;
import eu.elision.marketplace.web.dtos.cart.OrderLineDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.order.CustomerOrderDto;
import eu.elision.marketplace.web.dtos.order.OrderDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.dtos.users.CustomerDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class ControllerTest
{

    @Autowired
    Controller controller;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @Test
    void saveCostumerWithAddress()
    {
        final int initUserRepoSize = controller.findAllUsers().size();

        final Customer customer = new Customer();
        final Address address = new Address();

        final String city = RandomStringUtils.randomAlphabetic(5);
        final String number = String.valueOf(RandomUtils.nextInt());
        final String postalCode = String.valueOf(RandomUtils.nextInt());
        final String street = RandomStringUtils.randomAlphabetic(5);

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        address.setPostalCode(postalCode);
        address.setStreet(street);
        address.setNumber(number);
        address.setCity(city);

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        long customerId = controller.saveUser(customer).getId();

        assertThat(controller.findAllUsers()).hasSize(1 + initUserRepoSize);

        Customer customerFromRepo = (Customer) controller.findUserById(customerId);
        assertThat(customerFromRepo).isNotNull();

        assertThat(customerFromRepo.getEmail()).hasToString(email);
        assertThat(customerFromRepo.getFirstName()).hasToString(firstName);
        assertThat(customerFromRepo.getLastName()).hasToString(lastName);
    }

    @Test
    void saveCustomerWithoutAddress() {
        final int initUserRepoSize = controller.findAllUsers().size();

        final Customer customer = new Customer();

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));


        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        long customerId = controller.saveUser(customer).getId();

        assertThat(controller.findAllUsers()).hasSize(1 + initUserRepoSize);

        Customer customerFromRepo = (Customer) controller.findUserById(customerId);
        assertThat(customerFromRepo).isNotNull();

        assertThat(customerFromRepo.getEmail()).hasToString(email);
        assertThat(customerFromRepo.getFirstName()).hasToString(firstName);
        assertThat(customerFromRepo.getLastName()).hasToString(lastName);
    }

    @Test
    void findAllCategoriesTest() {
        assertThat(controller.findAllCategories()).isNotNull();
    }

    @Test
    void findAllCustomerDtoTest() {
        final int initSize = controller.findAllCustomerDto().size();

        controller.saveCustomer(new CustomerDto(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2)), String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT))));
        assertThat(controller.findAllCustomerDto()).hasSize(initSize + 1);
    }

    @Test
    void findProductsByVendor() {
        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));
        final String phone = RandomStringUtils.random(10, false, true);

        Vendor vendor = new Vendor();
        final String businessName = RandomStringUtils.randomAlphabetic(6);
        final String vatNumber = "BE0458402105";

        vendor.setVatNumber(vatNumber);
        vendor.setBusinessName(businessName);
        vendor.setFirstName(firstName);
        vendor.setLastName(lastName);
        vendor.setEmail(email);
        vendor.setPassword(password);
        vendor.setPhoneNumber(phone);
        controller.saveUser(vendor);

        Product product = new Product();
        product.setVendor(vendor);
        product.setTitle(RandomStringUtils.randomAlphabetic(5));
        controller.saveProduct(product);

        Collection<Product> products = controller.findProductsByVendor(vendor);

        assertThat(products).hasSize(1);
        assertThat(products.stream().anyMatch(product1 -> Objects.equals(product1.getTitle(), product.getTitle()))).isTrue();
    }

    @Test
    void addGetProductToCartTest() {
        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPassword(password);

        assertThat(customer.getCart().getOrderLines()).isEmpty();
        long customerId = controller.saveUser(customer).getId();

        Product product = new Product();
        final int price = RandomUtils.nextInt(1, 100);
        product.setPrice(price);
        final Vendor vendor = new Vendor();
        final String businessName = RandomStringUtils.randomAlphabetic(6);
        final String vatNumber = "BE0458402105";

        vendor.setVatNumber(vatNumber);
        vendor.setBusinessName(businessName);
        vendor.setLastName(RandomStringUtils.randomAlphabetic(4));
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(4));
        vendor.setPassword(password);
        vendor.setEmail(String.format("%s%s", email, RandomStringUtils.randomAlphabetic(3)));
        vendor.setPhoneNumber(RandomStringUtils.random(10, false, true));

        controller.saveUser(vendor);


        product.setVendor(vendor);

        final String description = RandomStringUtils.randomAlphabetic(5);
        product.setDescription(description);
        long productId = controller.saveProduct(product).getId();

        final int count = RandomUtils.nextInt(1, 10);
        controller.addProductToCart(email, new AddProductToCartDto(productId, count, false));

        CartDto cartDto = controller.getCustomerCart(email);
        assertThat(cartDto.orderLines().stream().anyMatch(orderLineDto -> Objects.equals(orderLineDto.product().description(), description))).isTrue();
        final OrderLineDto orderLineDto = cartDto.orderLines().stream().filter(old -> Objects.equals(old.product().description(), description)).findFirst().orElse(null);

        assertThat(orderLineDto).isNotNull();
        assertThat(orderLineDto.quantity()).isEqualTo(count);
        assertThat(orderLineDto.product().price()).isEqualTo(price);

        assertThat(cartDto.totalPrice()).isEqualTo(price * count);
    }

    @Test
    void deleteProductTest() {
        final int initSize = controller.findAllProducts().size();

        Product product = new Product();
        final long id = controller.saveProduct(product).getId();
        assertThat(controller.findAllProducts()).hasSize(initSize + 1);

        controller.deleteProduct(id);
        assertThat(controller.findAllProducts()).hasSize(initSize);
    }

    @Test
    void checkoutCartTest() {
        final Customer customer = new Customer();

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        final Vendor vendor = new Vendor();
        final String businessName = RandomStringUtils.randomAlphabetic(6);
        final String vatNumber = "BE0458402105";

        vendor.setVatNumber(vatNumber);
        vendor.setBusinessName(businessName);
        vendor.setLastName(RandomStringUtils.randomAlphabetic(4));
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(4));
        vendor.setPassword(password);
        vendor.setEmail(String.format("%s%s", email, RandomStringUtils.randomAlphabetic(3)));
        vendor.setPhoneNumber(RandomStringUtils.random(10, false, true));
        controller.saveUser(vendor);

        Product product = new Product();
        final int price = RandomUtils.nextInt(1, 100);
        product.setPrice(price);
        product.setVendor(vendor);
        userService.editUser(vendor);
        controller.saveUser(customer);

        final int count = RandomUtils.nextInt(1, 100);
        controller.addProductToCart(customer.getEmail(), new AddProductToCartDto(controller.saveProduct(product).getId(), count, false));


        userService.editUser(customer);
        final long orderId = controller.checkoutCart(email);
        Order order = controller.findOrderById(orderId);
        assertThat(order).isNotNull();
        assertThat(order.getUser().getFirstName()).isEqualTo(firstName);
        assertThat(order.getUser().getLastName()).isEqualTo(lastName);
        assertThat(order.getUser().getPassword()).isEqualTo(password);
        assertThat(order.getUser().getEmail()).isEqualTo(email);

        assertThat(customer.getCart().getOrderLines()).isEmpty();

    }

    @Test
    void getVendorOrders() {
        Vendor vendor = new Vendor();
        final String firstName = RandomStringUtils.randomAlphabetic(4);
        final String lastName = RandomStringUtils.randomAlphabetic(4);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String emailCustomer = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));
        final String phoneNumber = RandomStringUtils.random(10, false, true);
        final String businessName = RandomStringUtils.randomAlphabetic(6);
        final String vatNumber = "BE0458402105";

        vendor.setVatNumber(vatNumber);
        vendor.setBusinessName(businessName);
        vendor.setFirstName(firstName);
        vendor.setLastName(lastName);
        vendor.setEmail(email);
        vendor.setPassword(password);
        vendor.setPhoneNumber(phoneNumber);
        controller.saveUser(vendor);

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(emailCustomer);
        customer.setPassword(password);
        controller.saveUser(customer);

        Product product = new Product();
        product.setPrice(RandomUtils.nextDouble(1, 100.1));
        product.setVendor(vendor);
        controller.saveProduct(product);

        Product product2 = new Product();
        product2.setPrice(RandomUtils.nextDouble(1, 100.1));
        product2.setVendor(vendor);
        controller.saveProduct(product);

        Order order = new Order();
        OrderLine orderLine = new OrderLine();
        orderLine.setProduct(product);
        controller.saveOrderLine(orderLine);
        OrderLine orderLine2 = new OrderLine();
        orderLine2.setProduct(product);
        controller.saveOrderLine(orderLine2);
        order.getLines().add(orderLine2);
        order.setUser(customer);
        controller.saveOrder(order);

        Collection<OrderDto> orders = controller.getOrders(vendor.getEmail());

        assertThat(orders).hasSize(1);
        OrderDto orderDto = orders.stream().findFirst().orElse(null);
        assertThat(orderDto).isNotNull();
        assertThat(orderDto.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(orderDto.getOrderDate()).isEqualTo(order.getCreatedDate().toString());
        assertThat(orderDto.getCustomerName()).isEqualTo(order.getUser().getFullName());
        assertThat(orderDto.getTotalPrice()).isEqualTo(orderLine.getTotalPrice());

        orders = controller.getOrders(customer.getEmail());
        orderDto = orders.stream().findFirst().orElse(null);
        assertThat(orderDto).isNotNull();
        assertThat(orderDto.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(orderDto.getOrderDate()).isEqualTo(order.getCreatedDate().toString());
        assertThat(orderDto.getCustomerName()).isEqualTo(order.getUser().getFullName());
        assertThat(orderDto.getTotalPrice()).isEqualTo(orderLine.getTotalPrice());

        Admin admin = new Admin();
        admin.setEmail(String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2)));
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPassword(String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT)));
        controller.saveUser(admin);

        orders = controller.getOrders(admin.getEmail());
        assertThat(orders).isNotNull();
        assertThat(orders.stream().anyMatch(oDto -> oDto.getOrderNumber() == order.getOrderNumber())).isTrue();
        assertThat(orders.stream().anyMatch(oDto -> oDto.getTotalPrice() == order.getTotalPrice())).isTrue();
        assertThat(orders.stream().anyMatch(oDto -> Objects.equals(oDto.getOrderDate(), order.getCreatedDate().toString()))).isTrue();
        assertThat(orders.stream().anyMatch(oDto -> Objects.equals(oDto.getCustomerName(), order.getUser().getFullName()))).isTrue();
    }

    @Test
    void getOrdersUserNotFound() {
        final String userEmail = RandomStringUtils.randomAlphabetic(4);
        assertThrows(NotFoundException.class, () -> controller.getOrders(userEmail));
    }

    @Test
    void saveProductTest() {
        Vendor vendor = new Vendor();
        final String firstName = RandomStringUtils.randomAlphabetic(4);
        final String lastName = RandomStringUtils.randomAlphabetic(4);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));
        final String phoneNumber = RandomStringUtils.random(10, false, true);
        final String businessName = RandomStringUtils.randomAlphabetic(6);
        final String vatNumber = "BE0458402105";

        vendor.setVatNumber(vatNumber);
        vendor.setBusinessName(businessName);
        vendor.setFirstName(firstName);
        vendor.setLastName(lastName);
        vendor.setEmail(email);
        vendor.setPassword(password);
        vendor.setPhoneNumber(phoneNumber);
        controller.saveUser(vendor);

        final CategoryMakeDto categoryMakeDto = new CategoryMakeDto(RandomStringUtils.randomAlphabetic(5), 0L, new ArrayList<>());
        Category category = controller.saveCategory(categoryMakeDto);
        ProductDto productDto = new ProductDto(RandomUtils.nextInt(), RandomUtils.nextInt(), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), new ArrayList<>(), category, new ArrayList<>(), vendor.getId(), vendor.getBusinessName());

        Product product = controller.findProduct(controller.saveProduct(vendor, productDto).getId());

        assertThat(product.getDescription()).isEqualTo(productDto.description());
        assertThat(product.getPrice()).isEqualTo(productDto.price());
        assertThat(product.getTitle()).isEqualTo(productDto.title());
        assertThat(product.getVendor().getId()).isEqualTo(productDto.vendorId());
        assertThat(product.getAttributes()).hasSize(productDto.attributes().size());
        assertThat(product.getCategory().getName()).isEqualTo(productDto.category().getName());

    }

    @Test
    void getCustomerOrderTest() {
        Vendor vendor = new Vendor();
        Admin admin = new Admin();
        final String firstName = RandomStringUtils.randomAlphabetic(4);
        final String lastName = RandomStringUtils.randomAlphabetic(4);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String emailCustomer = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));
        final String phoneNumber = RandomStringUtils.random(10, false, true);
        final String businessName = RandomStringUtils.randomAlphabetic(6);
        final String vatNumber = "BE0458402105";

        vendor.setVatNumber(vatNumber);
        vendor.setBusinessName(businessName);
        vendor.setFirstName(firstName);
        vendor.setLastName(lastName);
        vendor.setEmail(email);
        vendor.setPassword(password);
        vendor.setPhoneNumber(phoneNumber);
        controller.saveUser(vendor);

        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setEmail(String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2)));
        admin.setPassword(password);
        controller.saveUser(admin);

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(emailCustomer);
        customer.setPassword(password);
        controller.saveUser(customer);

        Product product = new Product();
        product.setPrice(RandomUtils.nextDouble(1, 100.1));
        product.setVendor(vendor);
        controller.saveProduct(product);

        Product product2 = new Product();
        product2.setPrice(RandomUtils.nextDouble(1, 100.1));
        product2.setVendor(vendor);
        controller.saveProduct(product);

        Order order = new Order();
        OrderLine orderLine = new OrderLine();
        orderLine.setProduct(product);
        controller.saveOrderLine(orderLine);
        OrderLine orderLine2 = new OrderLine();
        orderLine2.setProduct(product);
        controller.saveOrderLine(orderLine2);
        order.getLines().add(orderLine2);
        order.setUser(customer);


        final long orderNumber = controller.saveOrder(order).getOrderNumber();
        CustomerOrderDto customerOrderDto = controller.getOrder(customer.getEmail(), orderNumber);
        CustomerOrderDto vendorOrderDto = controller.getOrder(vendor.getEmail(), orderNumber);
        CustomerOrderDto adminOrderDto = controller.getOrder(admin.getEmail(), orderNumber);

        assertThat(customerOrderDto.getId()).isEqualTo(orderNumber);
        assertThat(customerOrderDto.getCustomerMail()).isEqualTo(customer.getEmail());
        assertThat(customerOrderDto.getCustomerName()).isEqualTo(customer.getFullName());
        assertThat(customerOrderDto.getLines()).hasSize(order.getLines().size());
        assertThat(customerOrderDto.getTotalPrice()).isEqualTo(order.getTotalPrice());
        assertThat(customerOrderDto.getOrderDate()).isEqualTo(order.getCreatedDate());
        assertThat(vendorOrderDto.getId()).isEqualTo(orderNumber);
        assertThat(adminOrderDto.getId()).isEqualTo(orderNumber);
    }

    @Test
    void testEditCategory() {
        final int initCapRepo = controller.findAllCategories().size();
        Type[] types = {Type.STRING, Type.DECIMAL, Type.DECIMAL, Type.INTEGER};

        Category category = new Category();
        category.setName(RandomStringUtils.randomAlphabetic(5));
        final DynamicAttribute dynamicAttribute = new DynamicAttribute(RandomUtils.nextLong(), RandomStringUtils.randomAlphabetic(5), RandomUtils.nextBoolean(), types[RandomUtils.nextInt(0, 4)], category);
        category.setCharacteristics(List.of(
                dynamicAttribute

        ));
        category.setId(categoryService.save(category).getId());
        assertThat(controller.findAllCategories()).hasSize(initCapRepo + 1);

        HashSet<DynamicAttributeDto> hashSet = new HashSet<>();
        hashSet.add(new DynamicAttributeDto(RandomStringUtils.randomAlphabetic(5), RandomUtils.nextBoolean(), types[RandomUtils.nextInt(0, 4)]));

        CategoryDto editCategoryDto = new CategoryDto(category.getId(), RandomStringUtils.randomAlphabetic(10), 0L, hashSet);
        controller.editCategory(editCategoryDto);

        Category fromRepo = categoryService.findById(category.getId());

        assertThat(fromRepo).isNotEqualTo(category);
        assertThat(fromRepo.getName()).isNotEqualTo(category.getName());
        assertThat(fromRepo.getId()).isEqualTo(category.getId());
        assertThat(fromRepo.getCharacteristics()).hasSize(hashSet.size());
        assertThat(fromRepo.getName()).isNotEqualTo(category.getName());
    }

    @Test
    void getFakeCategoryTest() {
        assertThrows(NotFoundException.class, () -> controller.getCategory(-1));
    }
}