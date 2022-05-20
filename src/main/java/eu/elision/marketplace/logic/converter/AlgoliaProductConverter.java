package eu.elision.marketplace.logic.converter;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.logic.populator.Populator;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Convertor class to convert the products to Algolia products
 */
@Component
public class AlgoliaProductConverter extends Converter<Product, AlgoliaProductDto> {
    /**
     * Constructor of the AlgoliaProductConverter which takes the populators to later convert the objects with
     * @param populators A list of populators
     */
    protected AlgoliaProductConverter(List<Populator<Product, AlgoliaProductDto>> populators) {
        super(populators, AlgoliaProductDto.class);
    }
}
