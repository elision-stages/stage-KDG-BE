package eu.elision.marketplace.logic.services.algolia;

import com.algolia.search.SearchIndex;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.exceptions.ConversionException;
import eu.elision.marketplace.logic.converter.Converter;
import eu.elision.marketplace.repositories.ProductRepository;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(AlgoliaIndexerService.class);

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
    @Scheduled(cron = "0 0 0,12 * * *", zone = "Europe/Paris")
    public void indexAllProducts() {
        try {
            logger.info("Start indexing all products...");
            List<Product> productList = this.productRepository.findAll();
            logger.info("Found {} products in the repo", productList.size());
            Collection<AlgoliaProductDto> algoliaProductList = algoliaProductConverter.convertAll(productList);
            logger.info("Converted all {} products to Algolia products", algoliaProductList.size());
            SearchIndex<AlgoliaProductDto> searchClient;
            searchClient = indexSearchClientService.getSearchClient();
            logger.info("Retrieved the search client, saving the index now...");
            searchClient.saveObjects(algoliaProductList); // saveObjectsAsync
            logger.info("All done!");
        } catch (IOException e) {
            throw new ConversionException(e.getMessage());
        }
    }

    @Override
    public Product indexProduct(Product product)
    {
        try
        {
            AlgoliaProductDto algoliaProduct = algoliaProductConverter.convert(product);
            SearchIndex<AlgoliaProductDto> searchClient = indexSearchClientService.getSearchClient();
            searchClient.saveObject(algoliaProduct); // saveObjectAsync
        } catch (IOException e)
        {
            throw new ConversionException(e.getMessage());
        }
        return product;
    }
}
