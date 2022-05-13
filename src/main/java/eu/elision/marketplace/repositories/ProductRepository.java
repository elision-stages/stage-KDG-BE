package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Jpa repository for products
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Collection<Product> findProductsByVendor(Vendor vendor);
}