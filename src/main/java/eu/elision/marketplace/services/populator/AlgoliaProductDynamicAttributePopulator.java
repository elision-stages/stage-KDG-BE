package eu.elision.marketplace.services.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

@Component
public class AlgoliaProductDynamicAttributePopulator extends Populator<Product, AlgoliaProductDto> {

    @Override
    public void populate(Product source, AlgoliaProductDto target) {
        target.getParameters().put("dynamic", "demo value");
    }
}
