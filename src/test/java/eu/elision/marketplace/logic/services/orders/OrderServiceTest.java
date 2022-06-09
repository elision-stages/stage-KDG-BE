package eu.elision.marketplace.logic.services.orders;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Admin;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.repositories.OrderRepository;
import eu.elision.marketplace.web.dtos.order.CustomerOrderDto;
import eu.elision.marketplace.web.dtos.order.OrderDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest
{
    @InjectMocks
    OrderService orderService;
    @Mock
    OrderRepository orderRepository;

    @BeforeEach
    void setUp()
    {
        when(orderRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
    }

    @Test
    void save()
    {
        Order order = new Order();
        order.setOrderNumber(RandomUtils.nextLong());
        order.setLines(List.of(new OrderLine(RandomUtils.nextInt(), "", new Product(), RandomUtils.nextInt())));

        assertThat(orderService.save(order).getLines()).contains(order.getLines().get(0));
    }

    @Test
    void findOrderById()
    {
        Order order = new Order();
        order.setOrderNumber(RandomUtils.nextInt());

        when(orderRepository.findById(order.getOrderNumber())).thenReturn(Optional.of(order));
        assertThat(orderService.findOrderById(order.getOrderNumber())).isEqualTo(order);
    }

    @Test
    void findOrderByIdNotFound()
    {
        Order order = new Order();
        order.setOrderNumber(RandomUtils.nextInt());

        when(orderRepository.findById(order.getOrderNumber())).thenReturn(Optional.empty());

        final long orderNumber = order.getOrderNumber();
        Exception exception = assertThrows(NotFoundException.class, () -> orderService.findOrderById(orderNumber));

        assertThat(exception.getMessage()).isEqualTo(String.format("Order with id %s not found", order.getOrderNumber()));
    }

    @Test
    void findUserOrdersCustomer()
    {
        Customer customer = new Customer();

        final List<Order> customerOrders = List.of(new Order());
        final List<OrderDto> customerOrdersDto = List.of(new OrderDto(RandomUtils.nextLong(), RandomStringUtils.randomAlphabetic(50), RandomStringUtils.randomAlphabetic(50), RandomUtils.nextDouble(), RandomUtils.nextInt()));
        when(orderRepository.findAllByUser(customer)).thenReturn(customerOrders);

        try (MockedStatic<Mapper> utilities = Mockito.mockStatic(Mapper.class))
        {
            utilities.when(() -> Mapper.toOrderDto(customerOrders)).thenReturn(customerOrdersDto);

            assertThat(orderService.findUserOrders(customer)).isEqualTo(customerOrdersDto);
        }
    }

    @Test
    void findUserOrdersAdmin()
    {
        Admin admin = new Admin();

        final List<Order> customerOrders = List.of(new Order());
        final List<OrderDto> customerOrdersDto = List.of(new OrderDto(RandomUtils.nextLong(), RandomStringUtils.randomAlphabetic(50), RandomStringUtils.randomAlphabetic(50), RandomUtils.nextDouble(), RandomUtils.nextInt()));
        when(orderRepository.findAll()).thenReturn(customerOrders);

        try (MockedStatic<Mapper> utilities = Mockito.mockStatic(Mapper.class))
        {
            utilities.when(() -> Mapper.toOrderDto(customerOrders)).thenReturn(customerOrdersDto);

            assertThat(orderService.findUserOrders(admin)).isEqualTo(customerOrdersDto);
        }
    }

    @Test
    void findUserOrdersVendor()
    {
        Vendor vendor = new Vendor();
        final Customer customer = new Customer();
        customer.setFirstName(RandomStringUtils.randomAlphabetic(50));
        customer.setLastName(RandomStringUtils.randomAlphabetic(50));

        final Order vendorOrder = new Order();
        vendorOrder.setOrderNumber(RandomUtils.nextLong());
        vendorOrder.setUser(customer);
        vendorOrder.setLines(List.of(new OrderLine(RandomUtils.nextInt(), String.valueOf(vendorOrder.getOrderNumber()), new Product(), RandomUtils.nextInt()), new OrderLine(RandomUtils.nextInt(), String.valueOf(vendorOrder.getOrderNumber()), new Product(), RandomUtils.nextInt()), new OrderLine(RandomUtils.nextInt(), String.valueOf(vendorOrder.getOrderNumber()), new Product(), RandomUtils.nextInt())));

        final List<Order> vendorOrders = List.of(vendorOrder);
        when(orderRepository.findByLinesProductVendor(vendor)).thenReturn(vendorOrders);

        final Collection<OrderDto> userOrders = orderService.findUserOrders(vendor);
        assertThat(userOrders).hasSize(1);
        assertThat(userOrders.stream().anyMatch(orderDto -> orderDto.getOrderNumber() == vendorOrder.getOrderNumber())).isTrue();
    }

    @Test
    void getOrder()
    {
        Customer customer = new Customer();
        customer.setEmail(RandomStringUtils.randomAlphabetic(50));
        customer.setLastName(RandomStringUtils.randomAlphabetic(50));
        customer.setFirstName(RandomStringUtils.randomAlphabetic(50));

        Order order = new Order();
        order.setOrderNumber(RandomUtils.nextInt());
        order.setUser(customer);

        when(orderRepository.findById(order.getOrderNumber())).thenReturn(Optional.of(order));

        CustomerOrderDto customerOrderDto = orderService.getOrder(customer, order.getOrderNumber());
        assertThat(customerOrderDto.getId()).isEqualTo(order.getOrderNumber());
        assertThat(customerOrderDto.getCustomerMail()).isEqualTo(customer.getEmail());
        assertThat(customerOrderDto.getCustomerName()).isEqualTo(customer.getFullName());
        assertThat(customerOrderDto.getOrderDate()).isEqualTo(Date.valueOf(LocalDate.now()).toString());
        assertThat(customerOrderDto.getTotalPrice()).isEqualTo(order.getTotalPrice());
    }
}