package eu.elision.marketplace.services.converter;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.services.populator.Populator;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlgoliaProductConverter extends Converter<Product, AlgoliaProductDto> {
    protected AlgoliaProductConverter(List<Populator<Product, AlgoliaProductDto>> populators) {
        super(populators, AlgoliaProductDto.class);
    }
}
