package eu.elision.marketplace.logic.converter.populator.algolia;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.converter.populator.Populator;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Algolia populator that converts the product category to an Algolia category
 */

/**
 * Populate the product of an algolia product dto
 */
@Component
public class AlgoliaProductCategoryPopulator implements Populator<Product, AlgoliaProductDto> {

    @Override
    public void populate(Product source, AlgoliaProductDto target) {
        List<String> catStructure = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        Category current = source.getCategory();
        if (current != null) target.setCategoryId(source.getCategory().getId());
        while (current != null) {
            catStructure.add(current.getName());
            ids.add(current.getId());
            current = current.getParent();
        }
        Collections.reverse(catStructure);

        while (!catStructure.isEmpty()) {
            String paramName = String.format("categories.lvl%d", catStructure.size() - 1);
            String paramValue = String.join(" > ", catStructure);
            target.getParameters().put(paramName, paramValue);
            catStructure.remove(catStructure.size() - 1);
        }
        target.setCategoryIds(ids);
    }
}
