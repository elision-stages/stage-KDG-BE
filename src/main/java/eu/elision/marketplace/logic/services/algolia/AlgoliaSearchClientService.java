package eu.elision.marketplace.logic.services.algolia;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchConfig;
import com.algolia.search.SearchIndex;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(AlgoliaSearchClientService.class);

    @Override
    public SearchIndex<AlgoliaProductDto> getSearchClient() throws IOException {
        SearchConfig config = new SearchConfig.Builder(applicationId, apiKey)
                .setConnectTimeOut(2000)
                .setWriteTimeOut(30000)
                .setReadTimeOut(1000)
                .build();
        logger.info(String.format("Retrieving search client %s", String.format("%s_%s", indexPrefix, "kdg_stage_marketplace")));
        try (SearchClient client = DefaultSearchClient.create(config)) {
            return client.initIndex(String.format("%s_%s", indexPrefix, "kdg_stage_marketplace"), AlgoliaProductDto.class);
        }
    }
}
