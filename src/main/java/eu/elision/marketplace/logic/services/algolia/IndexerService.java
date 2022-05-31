package eu.elision.marketplace.logic.services.algolia;

import eu.elision.marketplace.domain.product.Product;

/**
 * Interface for index
 */
public interface IndexerService {
    /**
     * Indexes all the products
     */
    void indexAllProducts();

    /**
     * Indexes a single product
     */
    void indexProduct(Product product);
}
