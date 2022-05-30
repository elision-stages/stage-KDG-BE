package eu.elision.marketplace.logic.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;
import org.jsoup.Jsoup;

/**
 * Algolia populator that converts the product description to an Algolia description
 */
@Component
public class AlgoliaProductDescriptionPopulator implements Populator<Product, AlgoliaProductDto> {

    @Override
    public void populate(Product source, AlgoliaProductDto target) {
        String description = source.getDescription();
        if(description != null) {
            description = Jsoup.parse(source.getDescription()).text();
        }
        target.setDescription(description);
    }
}
