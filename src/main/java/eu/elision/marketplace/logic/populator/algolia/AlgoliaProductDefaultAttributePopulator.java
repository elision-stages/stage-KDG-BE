package eu.elision.marketplace.logic.populator.algolia;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.logic.populator.Populator;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

/**
 * Algolia populator that converts the default attributes (ID, title, vendor and price) to Algolia attributes
 */
@Component
public class AlgoliaProductDefaultAttributePopulator implements Populator<Product, AlgoliaProductDto>
{

    @Override
    public void populate(Product source, AlgoliaProductDto target)
    {
        target.setObjectID(source.getId());
        target.setName(source.getTitle());
        target.setVendor(source.getVendor().getBusinessName());
        if (source.getVendor() != null) target.setVendorId(source.getVendor().getId());
        target.setPrice(source.getPrice());
    }
}
