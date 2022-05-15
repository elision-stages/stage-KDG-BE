package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa repository for dynamic attributes
 */
public interface DynamicAttributeRepository extends JpaRepository<DynamicAttribute, Long> {
    DynamicAttribute findDynamicAttributeByName(String name);
}