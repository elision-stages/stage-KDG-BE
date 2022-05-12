package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.repositories.ProductRepository;
import eu.elision.marketplace.web.dtos.ProductDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

    private Product toProduct(ProductDto productDto, Collection<DynamicAttributeValue<?>> attributeValues, Vendor vendor) {
        Product product = new Product();
        product.setPrice(productDto.price());
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

    public Product findProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }
}
