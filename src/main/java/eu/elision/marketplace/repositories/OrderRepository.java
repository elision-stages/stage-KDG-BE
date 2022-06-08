package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Jpa interface for orders.
 */
public interface OrderRepository extends JpaRepository<Order, Long>
{
    /**
     * Find all orders that belong to a given user
     *
     * @param user the user whose orders you want
     * @return a collection of orders that belong to give user
     */
    Collection<Order> findAllByUser(User user);

    /**
     * Find orders by the vendor of the vendor
     *
     * @param vendor the vendor of the product of the order
     * @return a list of orders with given vendor products
     */
    List<Order> findByLinesProductVendor(Vendor vendor);
}