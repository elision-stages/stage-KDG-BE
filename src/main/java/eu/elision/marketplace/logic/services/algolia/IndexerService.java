package eu.elision.marketplace.logic.services.algolia;

import java.io.IOException;

/**
 * Interface for index
 */
public interface IndexerService {
    void indexAllProducts() throws IOException;
}
