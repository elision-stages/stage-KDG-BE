package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Jpa repository for dynamic attribute values
 */
public interface DynamicAttributeValueRepository extends JpaRepository<DynamicAttributeValue<?>, Long> {
}