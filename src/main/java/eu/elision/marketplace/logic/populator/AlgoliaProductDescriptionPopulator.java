package eu.elision.marketplace.logic.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

/**
 * Algolia populator that converts the product description to an Algolia description
 */
@Component
public class AlgoliaProductDescriptionPopulator implements Populator<Product, AlgoliaProductDto> {

    @Override
    public void populate(Product source, AlgoliaProductDto target) {
        target.setDescription(source.getDescription());
    }
}
