package eu.elision.marketplace.services;

import java.io.IOException;

/**
 * Interface for index
 */
public interface IndexerService {
    void indexAllProducts() throws IOException;
}
