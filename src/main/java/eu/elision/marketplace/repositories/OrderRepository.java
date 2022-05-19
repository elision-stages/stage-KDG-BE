package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Jpa interface for orders.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find all orders that belong to a given user
     *
     * @param user the user whose orders you want
     * @return a collection of orders that belong to give user
     */
    Collection<Order> findAllByUser(User user);
}