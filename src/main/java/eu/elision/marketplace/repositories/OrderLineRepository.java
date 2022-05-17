package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.orders.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for order lines
 */
public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
}