package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.users.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Jpa repository for cart
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
}