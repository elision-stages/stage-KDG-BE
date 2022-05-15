package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Cart;
import eu.elision.marketplace.repositories.CartRepository;
import org.springframework.stereotype.Service;

/**
 * Service for cart
 */
@Service
public class CartService {
    private final CartRepository cartRepository;


    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
