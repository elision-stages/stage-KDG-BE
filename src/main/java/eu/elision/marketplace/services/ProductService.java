package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.User;
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

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(ProductDto productDto, Collection<DynamicAttributeValue<?>> attributeValues, User vendor) {
        if (!(vendor instanceof Vendor)) throw new NotFoundException("No vendor with id %s found");
        productRepository.save(toProduct(productDto, attributeValues, (Vendor) vendor));
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
        if (productRepository.findById(product.getId()).orElse(null) == null)
            throw new NotFoundException(String.format("Product with id %s not found", product.getId()));
        productRepository.save(product);
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
