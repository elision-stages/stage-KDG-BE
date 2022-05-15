package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.repositories.OrderRepository;
import org.springframework.stereotype.Service;

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
        return repository.save(order);
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
}
