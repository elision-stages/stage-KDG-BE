package eu.elision.marketplace.services.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

@Component
public class AlgoliaProductImagePopulator implements Populator<Product, AlgoliaProductDto> {

    @Override
    public void populate(Product source, AlgoliaProductDto target) {
        if(source.getImages() == null || source.getImages().isEmpty()) {
            target.setImage("");
        }else {
            target.setImage(source.getImages().get(0));
        }
    }
}
