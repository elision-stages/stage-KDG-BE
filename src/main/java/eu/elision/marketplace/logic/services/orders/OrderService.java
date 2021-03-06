package eu.elision.marketplace.logic.services.orders;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.users.Admin;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.exceptions.UnauthorisedException;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.repositories.OrderRepository;
import eu.elision.marketplace.web.dtos.order.CustomerOrderDto;
import eu.elision.marketplace.web.dtos.order.OrderDto;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service for orders
 */
@Service
public record OrderService(OrderRepository repository)
{

    /**
     * Save an order
     *
     * @param order the order that needs to be saved
     * @return the saved order
     */
    public Order save(Order order)
    {
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
    public Order findOrderById(long orderId)
    {
        final Optional<Order> order = repository.findById(orderId);
        if (order.isEmpty()) throw new NotFoundException(String.format("Order with id %s not found", orderId));
        return order.get();
    }

    /**
     * Find the orders of a user
     *
     * @param user the user whose orders you need
     * @return a collection with order of given user
     */
    public Collection<OrderDto> findUserOrders(User user)
    {
        if (user instanceof Vendor vendor) return findVendorOrders(vendor);
        if (user instanceof Customer customer) return findCustomerOrders(customer);
        if (user instanceof Admin) return findAdminOrders();
        throw new UnauthorisedException(String.format("User with email %s is not a vendor, customer or admin", user.getEmail()));
    }

    /**
     * Get the orders of a given vendor
     *
     * @param vendor the vendor whose orders you want
     * @return a collection of vendor orders in dto format
     */
    private Collection<OrderDto> findVendorOrders(Vendor vendor)
    {
        List<Order> orders = repository.findByLinesProductVendor(vendor);
        ArrayList<OrderDto> vendorOrders = new ArrayList<>();

        for (Order order : orders)
        {
            for (OrderLine orderLine :
                    filterOrder(vendor, order))
            {
                final Optional<OrderDto> any = vendorOrders.stream().filter(orderDto -> String.valueOf(orderDto.getOrderNumber()).equals(orderLine.getOrderNumber())).findAny();
                if (any.isPresent())
                {
                    final OrderDto orderDto = any.get();
                    orderDto.setTotalPrice((orderDto.getTotalPrice() + orderLine.getTotalPrice()));
                    orderDto.setNumberProducts(orderDto.getNumberProducts() + orderLine.getQuantity());
                } else
                {
                    vendorOrders.add(new OrderDto(Long.parseLong(orderLine.getOrderNumber()), order.getUser().getFullName(), order.getCreatedDate().toString(), orderLine.getTotalPrice(), orderLine.getQuantity()));
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
    private Collection<OrderDto> findCustomerOrders(Customer customer)
    {
        return Mapper.toOrderDto(repository.findAllByUser(customer));
    }

    /**
     * Find orders for admin. Returns all the orders in the repository
     *
     * @return a collection of order dto
     */
    private Collection<OrderDto> findAdminOrders()
    {
        return Mapper.toOrderDto(repository.findAll());
    }

    /**
     * Get the order information of an order as a customer
     *
     * @param user User requesting the order information
     * @param id   ID of the order you want info about
     * @return CustomerOrderDto
     */
    public CustomerOrderDto getOrder(User user, long id)
    {
        Order order = findOrderById(id);
        if (
                !(user instanceof Admin) &&
                        !(Objects.equals(order.getUser().getEmail(), user.getEmail())) &&
                        !(user instanceof Vendor vendor && orderHasVendorProducts(vendor, order)))
            throw new UnauthorisedException("This isn't your order");

        Collection<OrderLine> orderLines = filterOrder(user, order);
        return new CustomerOrderDto(order.getOrderNumber(), order.getUser().getEmail(), order.getUser().getFullName(), orderLines.stream().map(Mapper::toOrderLineDto).toList(), orderLines.stream().mapToDouble(OrderLine::getTotalPrice).sum(), order.getCreatedDate());
    }

    private Collection<OrderLine> filterOrder(User user, Order order)
    {
        if (user instanceof Vendor)
            return order.getLines().stream().filter(line -> line.getProduct().getVendor().getId().equals(user.getId())).toList();
        return order.getLines();
    }

    private boolean orderHasVendorProducts(Vendor user, Order order)
    {
        return order.getLines().stream().anyMatch(line -> line.getProduct().getVendor().getId().equals(user.getId()));
    }
}
