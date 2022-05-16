package eu.elision.marketplace.services.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

@Component
public class AlgoliaProductDefaultAttributePopulator implements Populator<Product, AlgoliaProductDto> {

    @Override
    public void populate(Product source, AlgoliaProductDto target) {
        target.setObjectID(source.getId());
        target.setName(source.getTitle());
        target.setVendor(source.getVendor().getBusinessName());
        target.setPrice(source.getPrice());
    }
}
