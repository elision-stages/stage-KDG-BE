package eu.elision.marketplace.logic.services.orders;

import eu.elision.marketplace.domain.users.Cart;
import eu.elision.marketplace.repositories.CartRepository;
import org.springframework.stereotype.Service;

/**
 * Service for cart
 */
@Service
public class CartService {
    private final CartRepository cartRepository;

    /**
     * Constructor of the cart service that takes the cart repository
     * @param cartRepository Cart repository
     */
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Saves the shopping cart into the repository
     * @param cart The shopping cart
     */
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
