package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.repositories.OrderRepository;
import eu.elision.marketplace.web.dtos.cart.AddProductToCartDto;
import eu.elision.marketplace.web.dtos.cart.CartDto;
import eu.elision.marketplace.web.dtos.cart.OrderLineDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.order.VendorOrderDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.dtos.users.CustomerDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ControllerTest {


    @InjectMocks
    @Autowired
    Controller controller;
    @Mock
    OrderRepository orderRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCostumerWithAddress() {
        final int initUserRepoSize = controller.findAllUsers().size();
        final int initAddressRepoSize = controller.findAllAddresses().size();

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
        customer.setMainAddress(address);
        customer.setPassword(password);

        long addressId = controller.saveAddress(address).getId();
        long customerId = controller.saveUser(customer).getId();

        assertThat(controller.findAllAddresses()).hasSize(1 + initAddressRepoSize);
        assertThat(controller.findAllUsers()).hasSize(1 + initUserRepoSize);

        Address addressFromRepo = controller.findAddressById(addressId);
        Customer customerFromRepo = (Customer) controller.findUserById(customerId);
        assertThat(addressFromRepo).isNotNull();
        assertThat(customerFromRepo).isNotNull();

        assertThat(addressFromRepo.getPostalCode()).hasToString(postalCode);
        assertThat(addressFromRepo.getCity()).hasToString(city);
        assertThat(addressFromRepo.getNumber()).hasToString(number);
        assertThat(addressFromRepo.getStreet()).hasToString(street);

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
        assertThat(cartDto.orderLines().stream().anyMatch(orderLineDto -> Objects.equals(orderLineDto.productDto().description(), description))).isTrue();
        final OrderLineDto orderLineDto = cartDto.orderLines().stream().filter(old -> Objects.equals(old.productDto().description(), description)).findFirst().orElse(null);

        assertThat(orderLineDto).isNotNull();
        assertThat(orderLineDto.quantity()).isEqualTo(count);
        assertThat(orderLineDto.productDto().price()).isEqualTo(price);

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
        controller.saveUser(vendor);
        controller.saveUser(customer);

        final int count = RandomUtils.nextInt(1, 100);
        controller.addProductToCart(customer.getEmail(), new AddProductToCartDto(controller.saveProduct(product).getId(), count, false));


        controller.saveUser(customer);
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

        Order order = new Order();
        OrderLine orderLine = new OrderLine();
        orderLine.setVendor(vendor);
        orderLine.setProduct(product);
        controller.saveOrderLine(orderLine);
        order.getLines().add(orderLine);
        order.setUser(customer);
        controller.saveOrder(order);

        Collection<VendorOrderDto> orders = controller.getVendorOrders(vendor.getEmail());

        assertThat(orders).hasSize(1);
        final VendorOrderDto vendorOrderDto = orders.stream().findFirst().orElse(null);
        assertThat(vendorOrderDto).isNotNull();
        assertThat(vendorOrderDto.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(vendorOrderDto.getOrderDate()).isEqualTo(order.getCreatedDate().toString());
        assertThat(vendorOrderDto.getCustomerName()).isEqualTo(order.getUser().getFullName());
        assertThat(vendorOrderDto.getTotalPrice()).isEqualTo(orderLine.getTotalPrice());
    }

    @Test
    void saveProductTest() {
        Vendor vendor = new Vendor();
        final String firstName = RandomStringUtils.randomAlphabetic(4);
        final String lastName = RandomStringUtils.randomAlphabetic(4);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));
        final String phoneNumber = RandomStringUtils.random(10, false, true);

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

}