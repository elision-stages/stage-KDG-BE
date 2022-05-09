package eu.elision.marketplace.services;

import com.algolia.search.SearchIndex;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.repositories.ProductRepository;
import eu.elision.marketplace.services.converter.Converter;
import eu.elision.marketplace.web.dtos.AlgoliaProductDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AlgoliaIndexerService implements IndexerService {
    private final ProductRepository productRepository;
    private final SearchClientService<SearchIndex<AlgoliaProductDto>> indexSearchClientService;
    private final Converter<Product, AlgoliaProductDto> algoliaProductConverter;

    public AlgoliaIndexerService(ProductRepository productRepository, SearchClientService<SearchIndex<AlgoliaProductDto>> indexSearchClientService, Converter<Product, AlgoliaProductDto> algoliaProductConverter) {
        this.productRepository = productRepository;
        this.indexSearchClientService = indexSearchClientService;
        this.algoliaProductConverter = algoliaProductConverter;
    }

    @Override
    @Scheduled(cron = "0 0 0,12 * * *", zone = "Europe/Paris")
    public void indexAllProducts() {
        List<Product> productList = this.productRepository.findAll();
        Collection<AlgoliaProductDto> algoliaProductList = algoliaProductConverter.convertAll(productList);
        indexSearchClientService.getSearchClient().saveObjects(algoliaProductList);
    }
}
