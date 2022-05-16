package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.repositories.ProductRepository;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service for products
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DynamicAttributeValueService dynamicAttributeValueService;

    public ProductService(ProductRepository productRepository, DynamicAttributeValueService dynamicAttributeValueService) {
        this.productRepository = productRepository;
        this.dynamicAttributeValueService = dynamicAttributeValueService;
    }

    public void save(ProductDto productDto, Collection<DynamicAttributeValue<?>> attributeValues, Vendor vendor) {
        productRepository.save(toProduct(productDto, attributeValues, vendor));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    private Product toProduct(ProductDto productDto, Collection<DynamicAttributeValue<?>> attributeValues, Vendor vendor) {
        Product product = new Product();
        product.setPrice(productDto.price());
        product.setTitle(productDto.title());
        product.setVendor(vendor);
        product.setDescription(productDto.description());
        product.setImages(productDto.images());
        product.setCategory(productDto.category());
        product.setAttributes(attributeValues.stream().toList());

        return product;
    }

    public Collection<Product> findProductsByVendor(Vendor vendor) {
        return productRepository.findProductsByVendor(vendor);
    }

    public Collection<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Edit a product. Same as save but checks if the product you want to edit exists. If the product does't exist it will thorw a NotFoundException.
     *
     * @param product the product you want to save
     */
    public void editProduct(Product product) {
        final Product fromRepo = productRepository.findById(product.getId()).orElse(null);
        if (fromRepo == null)
            throw new NotFoundException(String.format("Product with id %s not found", product.getId()));

        fromRepo.getAttributes().addAll(product.getAttributes());
        fromRepo.setCategory(product.getCategory());
        dynamicAttributeValueService.deleteNonCategoryAttributes(fromRepo);
        productRepository.save(fromRepo);
    }

    public Product findProductById(long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new NotFoundException(String.format("Product with id %s not found", productId));
        }
        return product;
    }

    public void delete(long productId) {
        productRepository.deleteById(productId);
    }
}
