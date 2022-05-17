package eu.elision.marketplace.logic.services.orders;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.repositories.OrderRepository;
import eu.elision.marketplace.web.dtos.order.VendorOrderDto;
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
    public Collection<VendorOrderDto> findVendorOrders(Vendor vendor) {
        List<Order> orders = repository.findAll();
        ArrayList<VendorOrderDto> vendorOrders = new ArrayList<>();


        for (Order order : orders) {
            var test = order.getLines();
            final List<OrderLine> orderLines = test.stream().filter(ol -> Objects.equals(ol.getProduct().getVendor().getId(), vendor.getId())).toList();
            for (OrderLine orderLine : orderLines) {
                final VendorOrderDto vendorOrder = vendorOrders.stream().filter(vendorOrderDto -> Objects.equals(String.valueOf(vendorOrderDto.getOrderNumber()), orderLine.getOrderNumber())).findAny().orElse(null);

                if (vendorOrder == null) {
                    final VendorOrderDto voDto = new VendorOrderDto(Long.parseLong(orderLine.getOrderNumber()), order.getUser().getFullName(), Date.valueOf(order.getCreatedDate()).toString(), orderLine.getTotalPrice(), orderLine.getQuantity());
                    vendorOrders.add(voDto);
                } else {
                    vendorOrder.setTotalPrice(vendorOrder.getTotalPrice() + orderLine.getTotalPrice());
                    vendorOrder.setNumberProducts(vendorOrder.getNumberProducts() + orderLine.getQuantity());
                }
            }
        }
        return vendorOrders;
    }
}
