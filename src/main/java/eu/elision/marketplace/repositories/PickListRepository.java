package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.attributes.PickList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickListRepository extends JpaRepository<PickList, Long> {
}