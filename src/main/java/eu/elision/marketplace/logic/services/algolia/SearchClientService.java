package eu.elision.marketplace.logic.services.algolia;

import java.io.IOException;

/**
 * Interface to retrieve a search client
 * @param <T> Type that the client returns
 */
public interface SearchClientService<T> {
    T getSearchClient() throws IOException;
}
