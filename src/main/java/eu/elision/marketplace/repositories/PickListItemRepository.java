package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickListItemRepository extends JpaRepository<PickListItem, Long> {
}