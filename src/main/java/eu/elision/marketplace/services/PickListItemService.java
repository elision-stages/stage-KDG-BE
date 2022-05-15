package eu.elision.marketplace.services;

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

    public PickListItemService(PickListItemRepository pickListItemRepository) {
        this.pickListItemRepository = pickListItemRepository;
    }

    public void save(Collection<PickListItem> pickListItems) {
        pickListItemRepository.saveAll(pickListItems);
    }
}

