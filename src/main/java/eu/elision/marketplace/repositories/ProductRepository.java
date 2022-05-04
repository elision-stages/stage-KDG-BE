package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}