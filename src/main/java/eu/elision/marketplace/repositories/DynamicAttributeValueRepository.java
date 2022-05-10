package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DynamicAttributeValueRepository extends JpaRepository<DynamicAttributeValue<?>, Long> {
}