package eu.elision.marketplace.logic.services.algolia;

import java.io.IOException;

/**
 * Interface for index
 */
public interface IndexerService {
    /**
     * Indexes all the products
     * @throws IOException
     */
    void indexAllProducts() throws IOException;
}
