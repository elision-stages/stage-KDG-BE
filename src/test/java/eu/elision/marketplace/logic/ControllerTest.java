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
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.logic.services.orders.OrderService;
import eu.elision.marketplace.logic.services.product.CategoryService;
import eu.elision.marketplace.logic.services.product.DynamicAttributeService;
import eu.elision.marketplace.logic.services.product.ProductService;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.cart.AddProductToCartDto;
import eu.elision.marketplace.web.dtos.cart.CartDto;
import eu.elision.marketplace.web.dtos.cart.OrderLineDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import eu.elision.marketplace.web.dtos.order.CustomerOrderDto;
import eu.elision.marketplace.web.dtos.order.OrderDto;
import eu.elision.marketplace.web.dtos.users.VendorPageDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class ControllerTest
{
    @InjectMocks
    Controller controller;
    @Mock
    UserService userService;
    @Mock
    OrderService orderService;
    @Mock
    CategoryService categoryService;
    @Mock
    ProductService productService;
    @Mock
    DynamicAttributeService dynamicAttributeService;

    @Test
    void saveCostumerWithAddress()
    {
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

        customer.setId(RandomUtils.nextLong());
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        when(userService.save(customer)).thenReturn(customer);
        when(userService.findUserById(customer.getId())).thenReturn(customer);

        long customerId = controller.saveUser(customer).getId();

        Customer customerFromRepo = (Customer) controller.findUserById(customerId);
        assertThat(customerFromRepo).isNotNull();

        assertThat(customerFromRepo.getEmail()).hasToString(email);
        assertThat(customerFromRepo.getFirstName()).hasToString(firstName);
        assertThat(customerFromRepo.getLastName()).hasToString(lastName);
    }

    @Test
    void saveCustomerWithoutAddress()
    {
        final Customer customer = new Customer();

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        customer.setId(RandomUtils.nextLong());
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        when(userService.save(customer)).thenReturn(customer);
        when(userService.findUserById(customer.getId())).thenReturn(customer);

        long customerId = controller.saveUser(customer).getId();

        Customer customerFromRepo = (Customer) controller.findUserById(customerId);
        assertThat(customerFromRepo).isNotNull();

        assertThat(customerFromRepo.getEmail()).hasToString(email);
        assertThat(customerFromRepo.getFirstName()).hasToString(firstName);
        assertThat(customerFromRepo.getLastName()).hasToString(lastName);
    }

    @Test
    void findAllCategoriesTest()
    {
        List<Category> categories = List.of(new Category());
        when(categoryService.findAll()).thenReturn(categories);

        assertThat(controller.findAllCategories()).isEqualTo(categories);
    }

    @Test
    void findProductsByVendor()
    {
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

        Product product = new Product();
        product.setVendor(vendor);
        product.setTitle(RandomStringUtils.randomAlphabetic(5));

        when(productService.findProductsByVendorEmail(vendor.getEmail())).thenReturn(List.of(product));

        Collection<Product> products = controller.findProductsByVendor(vendor.getEmail());

        assertThat(products).hasSize(1);
        assertThat(products.stream().anyMatch(product1 -> Objects.equals(product1.getTitle(), product.getTitle()))).isTrue();
    }

    @Test
    void addGetProductToCartTest()
    {
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

        Product product = new Product();
        final int price = RandomUtils.nextInt(1, 100);
        product.setPrice(price);
        product.setId(RandomUtils.nextLong());
        final Vendor vendor = new Vendor();
        final String businessName = RandomStringUtils.randomAlphabetic(6);
        final String vatNumber = "BE0458402105";

        vendor.setId(RandomUtils.nextLong());
        vendor.setVatNumber(vatNumber);
        vendor.setBusinessName(businessName);
        vendor.setLastName(RandomStringUtils.randomAlphabetic(4));
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(4));
        vendor.setPassword(password);
        vendor.setEmail(String.format("%s%s", email, RandomStringUtils.randomAlphabetic(3)));
        vendor.setPhoneNumber(RandomStringUtils.random(10, false, true));

        product.setVendor(vendor);

        when(productService.save(product)).thenReturn(product);

        final String description = RandomStringUtils.randomAlphabetic(5);
        product.setDescription(description);
        long productId = controller.saveProduct(product).getId();

        when(userService.findUserByEmail(customer.getEmail())).thenReturn(customer);
        when(productService.findProductById(productId)).thenReturn(product);

        final int count = RandomUtils.nextInt(1, 10);
        controller.addProductToCart(email, new AddProductToCartDto(productId, count, false));

        when(userService.getUserCart(customer.getEmail())).thenReturn(customer.getCart());

        CartDto cartDto = controller.findCustomerCart(email);
        assertThat(cartDto.orderLines().stream().anyMatch(orderLineDto -> Objects.equals(orderLineDto.product().description(), description))).isTrue();
        final OrderLineDto orderLineDto = cartDto.orderLines().stream().filter(old -> Objects.equals(old.product().description(), description)).findFirst().orElse(null);

        assertThat(orderLineDto).isNotNull();
        assertThat(orderLineDto.quantity()).isEqualTo(count);
        assertThat(orderLineDto.product().price()).isEqualTo(price);

        assertThat(cartDto.totalPrice()).isEqualTo(price * count);
    }

    @Test
    void checkoutCartTest()
    {
        final Customer customer = new Customer();

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        customer.setId(RandomUtils.nextLong());
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        final Vendor vendor = new Vendor();
        final String businessName = RandomStringUtils.randomAlphabetic(6);
        final String vatNumber = "BE0458402105";

        vendor.setId(RandomUtils.nextLong());
        vendor.setVatNumber(vatNumber);
        vendor.setBusinessName(businessName);
        vendor.setLastName(RandomStringUtils.randomAlphabetic(4));
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(4));
        vendor.setPassword(password);
        vendor.setEmail(String.format("%s%s", email, RandomStringUtils.randomAlphabetic(3)));
        vendor.setPhoneNumber(RandomStringUtils.random(10, false, true));

        Product product = new Product();
        final int price = RandomUtils.nextInt(1, 100);
        product.setId(RandomUtils.nextLong());
        product.setPrice(price);
        product.setVendor(vendor);

        when(productService.save(product)).thenReturn(product);
        when(userService.findUserByEmail(customer.getEmail())).thenReturn(customer);
        when(userService.findCustomerByEmail(customer.getEmail())).thenReturn(customer);
        when(productService.findProductById(product.getId())).thenReturn(product);


        final int count = RandomUtils.nextInt(1, 100);
        customer.setCart(controller.addProductToCart(customer.getEmail(), new AddProductToCartDto(controller.saveProduct(product).getId(), count, false)));

        Order toReturn = new Order();
        toReturn.setUser(customer);
        toReturn.setLines(customer.getCart().getOrderLines());
        toReturn.setOrderNumber(RandomUtils.nextInt());

        when(orderService.save(any())).thenReturn(toReturn);
        when(orderService.findOrderById(toReturn.getOrderNumber())).thenReturn(toReturn);

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
    void getVendorOrders()
    {
        Vendor vendor = new Vendor();
        final String firstName = RandomStringUtils.randomAlphabetic(4);
        final String lastName = RandomStringUtils.randomAlphabetic(4);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String emailCustomer = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));
        final String phoneNumber = RandomStringUtils.random(10, false, true);
        final String businessName = RandomStringUtils.randomAlphabetic(6);
        final String vatNumber = "BE0458402105";

        vendor.setId(RandomUtils.nextLong());
        vendor.setVatNumber(vatNumber);
        vendor.setBusinessName(businessName);
        vendor.setFirstName(firstName);
        vendor.setLastName(lastName);
        vendor.setEmail(email);
        vendor.setPassword(password);
        vendor.setPhoneNumber(phoneNumber);

        Customer customer = new Customer();
        customer.setId(RandomUtils.nextLong());
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(emailCustomer);
        customer.setPassword(password);

        Product product = new Product();
        product.setId(RandomUtils.nextLong());
        product.setPrice(RandomUtils.nextDouble(1, 100.1));
        product.setVendor(vendor);

        Product product2 = new Product();
        product2.setId(RandomUtils.nextLong());
        product2.setPrice(RandomUtils.nextDouble(1, 100.1));
        product2.setVendor(vendor);

        Order order = new Order();
        order.setOrderNumber(RandomUtils.nextInt());

        OrderLine orderLine = new OrderLine();
        orderLine.setOrderLineNumber(RandomUtils.nextInt());
        orderLine.setProduct(product);

        OrderLine orderLine2 = new OrderLine();
        orderLine2.setOrderLineNumber(RandomUtils.nextInt());
        orderLine2.setProduct(product);

        order.getLines().addAll(List.of(orderLine, orderLine2));
        order.setUser(customer);

        when(userService.findUserByEmail(vendor.getEmail())).thenReturn(vendor);
        when(orderService.findUserOrders(vendor)).thenReturn(List.of(Mapper.toOrderDto(order)));

        Collection<OrderDto> orders = controller.findUserOrders(vendor.getEmail());

        assertThat(orders).hasSize(1);
        OrderDto orderDto = orders.stream().findFirst().orElse(null);
        assertThat(orderDto).isNotNull();
        assertThat(orderDto.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(orderDto.getOrderDate()).isEqualTo(order.getCreatedDate().toString());
        assertThat(orderDto.getCustomerName()).isEqualTo(order.getUser().getFullName());
        assertThat(orderDto.getTotalPrice()).isEqualTo(orderLine.getTotalPrice());

        when(userService.findUserByEmail(customer.getEmail())).thenReturn(customer);
        when(orderService.findUserOrders(customer)).thenReturn(List.of(Mapper.toOrderDto(order)));

        orders = controller.findUserOrders(customer.getEmail());
        orderDto = orders.stream().findFirst().orElse(null);
        assertThat(orderDto).isNotNull();
        assertThat(orderDto.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(orderDto.getOrderDate()).isEqualTo(order.getCreatedDate().toString());
        assertThat(orderDto.getCustomerName()).isEqualTo(order.getUser().getFullName());
        assertThat(orderDto.getTotalPrice()).isEqualTo(orderLine.getTotalPrice());

        Admin admin = new Admin();
        admin.setId(RandomUtils.nextLong());
        admin.setEmail(String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2)));
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPassword(String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT)));

        when(userService.findUserByEmail(admin.getEmail())).thenReturn(admin);
        when(orderService.findUserOrders(admin)).thenReturn(List.of(Mapper.toOrderDto(order)));

        orders = controller.findUserOrders(admin.getEmail());
        assertThat(orders).isNotNull();
        assertThat(orders.stream().anyMatch(oDto -> oDto.getOrderNumber() == order.getOrderNumber())).isTrue();
        assertThat(orders.stream().anyMatch(oDto -> oDto.getTotalPrice() == order.getTotalPrice())).isTrue();
        assertThat(orders.stream().anyMatch(oDto -> Objects.equals(oDto.getOrderDate(), order.getCreatedDate().toString()))).isTrue();
        assertThat(orders.stream().anyMatch(oDto -> Objects.equals(oDto.getCustomerName(), order.getUser().getFullName()))).isTrue();
    }

    @Test
    void getOrdersUserNotFound()
    {
        final String userEmail = RandomStringUtils.randomAlphabetic(4);

        when(userService.findUserByEmail(userEmail)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> controller.findUserOrders(userEmail));
    }

    @Test
    void getCustomerOrderTest()
    {
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

        vendor.setId(RandomUtils.nextLong());
        vendor.setVatNumber(vatNumber);
        vendor.setBusinessName(businessName);
        vendor.setFirstName(firstName);
        vendor.setLastName(lastName);
        vendor.setEmail(email);
        vendor.setPassword(password);
        vendor.setPhoneNumber(phoneNumber);

        admin.setId(RandomUtils.nextLong());
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setEmail(String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2)));
        admin.setPassword(password);

        Customer customer = new Customer();
        customer.setId(RandomUtils.nextLong());
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(emailCustomer);
        customer.setPassword(password);

        Product product = new Product();
        product.setId(RandomUtils.nextLong());
        product.setPrice(RandomUtils.nextDouble(1, 100.1));
        product.setVendor(vendor);

        Product product2 = new Product();
        product2.setId(RandomUtils.nextLong());
        product2.setPrice(RandomUtils.nextDouble(1, 100.1));
        product2.setVendor(vendor);

        Order order = new Order();

        OrderLine orderLine = new OrderLine();
        orderLine.setOrderLineNumber(RandomUtils.nextInt());
        orderLine.setProduct(product);

        OrderLine orderLine2 = new OrderLine();
        orderLine2.setOrderLineNumber(RandomUtils.nextInt());
        orderLine2.setProduct(product);


        order.getLines().addAll(List.of(orderLine, orderLine2));
        order.setUser(customer);

        CustomerOrderDto toReturn = new CustomerOrderDto(order.getOrderNumber(), order.getUser().getEmail(), order.getUser().getFullName(), order.getLines().stream().map(Mapper::toOrderLineDto).toList(), order.getTotalPrice(), order.getCreatedDate());

        when(orderService.getOrder(any(), eq(order.getOrderNumber()))).thenReturn(toReturn);
        when(orderService.save(order)).thenReturn(order);

        final long orderNumber = controller.saveOrder(order).getOrderNumber();
        CustomerOrderDto customerOrderDto = controller.findOrder(customer.getEmail(), orderNumber);
        CustomerOrderDto vendorOrderDto = controller.findOrder(vendor.getEmail(), orderNumber);
        CustomerOrderDto adminOrderDto = controller.findOrder(admin.getEmail(), orderNumber);

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
    void testEditCategory()
    {
        Type[] types = {Type.STRING, Type.DECIMAL, Type.DECIMAL, Type.INTEGER};

        Category category = new Category();
        category.setId(RandomUtils.nextLong());
        category.setName(RandomStringUtils.randomAlphabetic(5));
        final DynamicAttribute dynamicAttribute = new DynamicAttribute(RandomUtils.nextLong(), RandomStringUtils.randomAlphabetic(5), RandomUtils.nextBoolean(), types[RandomUtils.nextInt(0, 4)], category);
        category.setCharacteristics(List.of(dynamicAttribute

        ));

        HashSet<DynamicAttributeDto> hashSet = new HashSet<>();
        final DynamicAttributeDto newDA = new DynamicAttributeDto(RandomStringUtils.randomAlphabetic(5), RandomUtils.nextBoolean(), types[RandomUtils.nextInt(0, 4)]);
        hashSet.add(newDA);

        CategoryDto editCategoryDto = new CategoryDto(category.getId(), RandomStringUtils.randomAlphabetic(10), 0L, hashSet);

        final DynamicAttribute dynamicAttribute1 = new DynamicAttribute(RandomUtils.nextLong(), newDA.getName(), newDA.isRequired(), newDA.getType(), category);
        List<DynamicAttribute> convertedDA = new ArrayList<>(List.of(dynamicAttribute1));

        Category editedCategory = new Category();
        editedCategory.setId(editCategoryDto.getId());
        editedCategory.setName(editCategoryDto.getName());
        editedCategory.setCharacteristics(convertedDA);


        when(dynamicAttributeService.renewAttributes(editCategoryDto, category)).thenReturn(convertedDA);
        when(categoryService.findById(category.getId())).thenReturn(category);
        when(categoryService.editCategory(editCategoryDto, convertedDA)).thenReturn(editedCategory);

        Category fromRepo = controller.editCategory(editCategoryDto);

        assertThat(fromRepo).isNotEqualTo(category);
        assertThat(fromRepo.getName()).isNotEqualTo(category.getName());
        assertThat(fromRepo.getId()).isEqualTo(category.getId());
        assertThat(fromRepo.getCharacteristics()).hasSize(hashSet.size());
        assertThat(fromRepo.getName()).isNotEqualTo(category.getName());
    }

    @Test
    void getFakeCategoryTest()
    {
        long wrongId = RandomUtils.nextInt();
        when(categoryService.findById(wrongId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> controller.findCategoryById(wrongId));
    }

    @Test
    void testGetVendorById()
    {
        Vendor vendor = new Vendor();
        vendor.setId(RandomUtils.nextLong());
        vendor.setLogo(RandomStringUtils.randomAlphabetic(50));
        vendor.setTheme(RandomStringUtils.randomAlphabetic(50));
        vendor.setIntroduction(RandomStringUtils.randomAlphabetic(50));
        vendor.setVatNumber("BE0458402105");
        vendor.setPhoneNumber(RandomStringUtils.random(50, false, true));
        vendor.setBusinessName(RandomStringUtils.randomAlphabetic(50));
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(50));
        vendor.setLastName(RandomStringUtils.randomAlphabetic(50));
        vendor.setEmail(String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2)));
        vendor.setPassword(String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT)));
        vendor.setValidated(RandomUtils.nextBoolean());

        when(userService.findVendorById(vendor.getId())).thenReturn(vendor);
        when(productService.findProductsByVendorId(vendor.getId())).thenReturn(new ArrayList<>());

        final VendorPageDto vendorDto = controller.findVendorById(vendor.getId());
        assertThat(vendorDto.theme()).isEqualTo(vendor.getTheme());
        assertThat(vendorDto.introduction()).isEqualTo(vendor.getIntroduction());
        assertThat(vendorDto.vatNumber()).isEqualTo(vendor.getVatNumber());
        assertThat(vendorDto.phoneNumber()).isEqualTo(vendor.getPhoneNumber());
        assertThat(vendorDto.businessName()).isEqualTo(vendor.getBusinessName());
        assertThat(vendorDto.email()).isEqualTo(vendor.getEmail());
    }
}