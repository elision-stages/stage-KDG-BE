package eu.elision.marketplace.services;

import java.io.IOException;

public interface SearchClientService<T> {
    T getSearchClient() throws IOException;
}
