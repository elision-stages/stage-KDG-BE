package eu.elision.marketplace.logic.services.orders;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.users.*;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.repositories.OrderRepository;
import eu.elision.marketplace.web.dtos.order.OrderDto;
import eu.elision.marketplace.web.dtos.order.CustomerOrderDto;
import eu.elision.marketplace.web.dtos.order.VendorOrderDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import eu.elision.marketplace.web.webexceptions.UnauthorisedException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Service for orders
 */
@Service
public class OrderService {
    private final OrderRepository repository;

    /**
     * Public constructor
     *
     * @param repository the repository with orders
     */

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Save an order
     *
     * @param order the order that needs to be saved
     * @return the saved order
     */
    public Order save(Order order) {
        Order saved = repository.save(order);
        saved.getLines().forEach(orderLine -> orderLine.setOrderNumber(String.valueOf(order.getOrderNumber())));
        repository.save(saved);
        return saved;
    }

    /**
     * Find an order by id
     *
     * @param orderId the id of the order
     * @return the order with given id
     */
    public Order findOrderById(long orderId) {
        return repository.findById(orderId).orElse(null);
    }

    /**
     * Get the orders of a given vendor
     *
     * @param vendor the vendor whose orders you want
     * @return a collection of vendor orders in dto format
     */
    public Collection<OrderDto> findVendorOrders(Vendor vendor) {
        List<Order> orders = repository.findAll();
        ArrayList<OrderDto> vendorOrders = new ArrayList<>();


        for (Order order : orders) {
            var test = order.getLines();
            final List<OrderLine> orderLines = test.stream().filter(ol -> Objects.equals(ol.getProduct().getVendor().getId(), vendor.getId())).toList();
            for (OrderLine orderLine : orderLines) {
                final OrderDto vendorOrder = vendorOrders.stream().filter(vendorOrderDto -> Objects.equals(String.valueOf(vendorOrderDto.getOrderNumber()), orderLine.getOrderNumber())).findAny().orElse(null);

                if (vendorOrder == null) {
                    final OrderDto voDto = new OrderDto(Long.parseLong(orderLine.getOrderNumber()), order.getUser().getFullName(), Date.valueOf(order.getCreatedDate()).toString(), orderLine.getTotalPrice(), orderLine.getQuantity());
                    vendorOrders.add(voDto);
                } else {
                    vendorOrder.setTotalPrice(vendorOrder.getTotalPrice() + orderLine.getTotalPrice());
                    vendorOrder.setNumberProducts(vendorOrder.getNumberProducts() + orderLine.getQuantity());
                }
            }
        }
        return vendorOrders;
    }

    /**
     * Find all the orders of a customer
     *
     * @param customer the customer whose orders you need
     * @return a collection of order dto
     */
    public Collection<OrderDto> findCustomerOrders(Customer customer) {
        return Mapper.toOrderDto(repository.findAllByUser(customer));
    }

    /**
     * Find orders for admin. Returns all the orders in the repository
     *
     * @return a collection of order dto
     */
    public Collection<OrderDto> findAdminOrders() {
        return Mapper.toOrderDto(repository.findAll());
    }

    /**
     * Get the order information of an order as a customer
     * @param user User requesting the order information
     * @param id ID of the order you want info about
     * @return CustomerOrderDto
     */
    public CustomerOrderDto getCustomerOrder(User user, long id) {
        Order order = repository.findById(id).orElse(null);
        if(order == null) throw new NotFoundException("Order not found");
        if(!(user instanceof Admin) && !order.getUser().getEmail().equals(user.getEmail())) throw new UnauthorisedException("This isn't your order");
        return new CustomerOrderDto(
                order.getOrderNumber(),
                order.getUser().getEmail(),
                order.getUser().getFullName(),
                order.getLines().stream().map(Mapper::toOrderLineDto).toList(),
                order.getTotalPrice(),
                order.getCreatedDate()
                );
    }
}
