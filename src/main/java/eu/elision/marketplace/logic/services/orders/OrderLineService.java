package eu.elision.marketplace.logic.services.orders;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.repositories.OrderLineRepository;
import org.springframework.stereotype.Service;

/**
 * A service for order lines
 */
@Service
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;

    /**
     * Public constructor
     *
     * @param orderLineRepository the order line repository that the service has to use
     */
    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    /**
     * Save an order line
     *
     * @param orderLine the order line you want to save
     */
    public void save(OrderLine orderLine) {
        orderLineRepository.save(orderLine);
    }
}
