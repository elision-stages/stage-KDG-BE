package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.users.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}