package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService
{
    private final OrderRepository repository;


    public OrderService(OrderRepository repository)
    {
        this.repository = repository;
    }

    public Order save(Order order)
    {
        return repository.save(order);
    }

    public Order findOrderById(long orderId)
    {
        return repository.findById(orderId).orElse(null);
    }
}
