package eu.elision.marketplace.logic.services.algolia;

import java.io.IOException;

/**
 * Interface for search clients
 */
public interface SearchClientService<T> {
    T getSearchClient() throws IOException;
}
