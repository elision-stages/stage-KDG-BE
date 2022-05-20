package eu.elision.marketplace.logic.populator;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Algolia populator that converts the product category to an Algolia category
 */
@Component
public class AlgoliaProductCategoryPopulator implements Populator<Product, AlgoliaProductDto> {

    @Override
    public void populate(Product source, AlgoliaProductDto target) {
        List<String> catStructure = new ArrayList<>();
        Category current = source.getCategory();
        while (current != null) {
            catStructure.add(current.getName());
            current = current.getParent();
        }
        Collections.reverse(catStructure);

        while (!catStructure.isEmpty()) {
            String paramName = String.format("categories.lvl%d", catStructure.size() - 1);
            String paramValue = String.join(" > ", catStructure);
            target.getParameters().put(paramName, paramValue);
            catStructure.remove(catStructure.size() - 1);
        }
    }
}
