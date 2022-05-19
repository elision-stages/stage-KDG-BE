package eu.elision.marketplace.logic.services.product;

import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.repositories.PickListItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service for pick list items
 */
@Service
public class PickListItemService {
    private final PickListItemRepository pickListItemRepository;

    /**
     * Public constructor
     *
     * @param pickListItemRepository the pick list repository that the service has to use
     */
    public PickListItemService(PickListItemRepository pickListItemRepository) {
        this.pickListItemRepository = pickListItemRepository;
    }

    /**
     * Save a list of pick list items
     *
     * @param pickListItems the pick list item s to be saved
     */
    public void save(Collection<PickListItem> pickListItems) {
        pickListItemRepository.saveAll(pickListItems);
    }
}

