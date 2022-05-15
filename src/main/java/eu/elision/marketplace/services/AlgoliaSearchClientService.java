package eu.elision.marketplace.services;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchConfig;
import com.algolia.search.SearchIndex;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service for algolia search client
 */
@Service
public class AlgoliaSearchClientService implements SearchClientService<SearchIndex<AlgoliaProductDto>> {
    @Value("${algolia.applicationId}")
    private String applicationId;
    @Value("${algolia.apiKey}")
    private String apiKey;
    @Value("${algolia.indexPrefix:l1}")
    private String indexPrefix;

    @Override
    public SearchIndex<AlgoliaProductDto> getSearchClient() throws IOException {
        SearchConfig config = new SearchConfig.Builder(applicationId, apiKey).build();
        try (SearchClient client = DefaultSearchClient.create(config)) {
            return client.initIndex(String.format("%s_%s", indexPrefix, "kdg_stage_marketplace"), AlgoliaProductDto.class);
        }
    }
}
