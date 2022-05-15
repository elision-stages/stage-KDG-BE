package eu.elision.marketplace.services;

import java.io.IOException;

/**
 * Interface for search clients
 */
public interface SearchClientService<T> {
    T getSearchClient() throws IOException;
}
