package eu.elision.marketplace.logic.services.product;

import eu.elision.marketplace.domain.product.category.attributes.PickList;
import eu.elision.marketplace.repositories.PickListRepository;
import org.springframework.stereotype.Service;

/**
 * Service for pick lists
 */
@Service
public class PickListService {
    private final PickListRepository pickListRepository;

    public PickListService(PickListRepository pickListRepository) {
        this.pickListRepository = pickListRepository;
    }

    public void save(PickList pickList) {
        pickListRepository.save(pickList);
    }
}
