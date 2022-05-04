package eu.elision.marketplace.services.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

@Component
public class AlgoliaProductImagePopulator extends Populator<Product, AlgoliaProductDto> {

    @Override
    public void populate(Product source, AlgoliaProductDto target) {
        if(source.getImages().size() > 0) {
            target.setImage(source.getImages().get(0));
        }else {
            target.setImage("");
        }
    }
}
