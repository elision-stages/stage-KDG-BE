package eu.elision.marketplace.services.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

@Component
public class AlgoliaProductDynamicAttributePopulator implements Populator<Product, AlgoliaProductDto> {

    @Override
    public void populate(Product source, AlgoliaProductDto target) {
        source.getAttributes().forEach(attr -> target.getParameters().put(attr.getAttributeName(), attr.getValue()));
    }
}
