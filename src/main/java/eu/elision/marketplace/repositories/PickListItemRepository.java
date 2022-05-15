package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Jpa repository for pick list items
 */
public interface PickListItemRepository extends JpaRepository<PickListItem, Long> {
}