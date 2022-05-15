package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa interface for orders.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}