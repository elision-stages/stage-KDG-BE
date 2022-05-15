package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.attributes.PickList;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Jpa repository for pick lists
 */
public interface PickListRepository extends JpaRepository<PickList, Long> {
}