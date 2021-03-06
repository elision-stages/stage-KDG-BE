package eu.elision.marketplace.logic.converter.populator.algolia;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.logic.converter.populator.Populator;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

/**
 * Algolia populator that converts the product's dynamic attributes to an Algolia attributes
 */
@Component
public class AlgoliaProductDynamicAttributePopulator implements Populator<Product, AlgoliaProductDto>
{

    @Override
    public void populate(Product source, AlgoliaProductDto target)
    {
        source.getAttributes().forEach(attr -> target.getParameters().put(attr.getAttributeName(), attr.getValue().toString()));
    }
}
