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

    /**
     * Constructor of the PickListService
     * @param pickListRepository PickListRepository (autowired)
     */
    public PickListService(PickListRepository pickListRepository) {
        this.pickListRepository = pickListRepository;
    }

    /**
     * Save a PickList
     * @param pickList PickList to save
     */
    public void save(PickList pickList) {
        pickListRepository.save(pickList);
    }
}
