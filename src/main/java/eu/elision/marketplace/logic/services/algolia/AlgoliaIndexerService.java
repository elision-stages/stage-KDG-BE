package eu.elision.marketplace.logic.services.algolia;

import com.algolia.search.SearchIndex;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.logic.converter.Converter;
import eu.elision.marketplace.repositories.ProductRepository;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Service for algolia indexer
 */
@Service
public class AlgoliaIndexerService implements IndexerService {
    private final ProductRepository productRepository;
    private final SearchClientService<SearchIndex<AlgoliaProductDto>> indexSearchClientService;
    private final Converter<Product, AlgoliaProductDto> algoliaProductConverter;

    /**
     * Public constructor
     *
     * @param productRepository        autowired constructor
     * @param indexSearchClientService autowired service
     * @param algoliaProductConverter  autowired converter
     */
    public AlgoliaIndexerService(ProductRepository productRepository, SearchClientService<SearchIndex<AlgoliaProductDto>> indexSearchClientService, Converter<Product, AlgoliaProductDto> algoliaProductConverter) {
        this.productRepository = productRepository;
        this.indexSearchClientService = indexSearchClientService;
        this.algoliaProductConverter = algoliaProductConverter;
    }

    @Override
    //@Scheduled(cron = "0 0 0,12 * * *", zone = "Europe/Paris")
    @Scheduled(cron = "0 0,10,20,30,40,50 * * * *", zone = "Europe/Paris")
    public void indexAllProducts() {
        try {
            List<Product> productList = this.productRepository.findAll();
            Collection<AlgoliaProductDto> algoliaProductList = algoliaProductConverter.convertAll(productList);
            SearchIndex<AlgoliaProductDto> searchClient = null;
            searchClient = indexSearchClientService.getSearchClient();
            searchClient.saveObjects(algoliaProductList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
