package eu.elision.marketplace.logic.services.users;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.logic.services.product.DynamicAttributeValueService;
import eu.elision.marketplace.repositories.ProductRepository;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.webexceptions.InvalidDataException;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import eu.elision.marketplace.web.webexceptions.UnauthorisedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Service for products
 */
@Service
public class ProductService
{
    private final ProductRepository productRepository;
    private final DynamicAttributeValueService dynamicAttributeValueService;

    /**
     * Public constructor
     *
     * @param productRepository            the product repository
     * @param dynamicAttributeValueService dynamic attribute service
     */
    public ProductService(ProductRepository productRepository, DynamicAttributeValueService dynamicAttributeValueService)
    {
        this.productRepository = productRepository;
        this.dynamicAttributeValueService = dynamicAttributeValueService;
    }

    /**
     * Save a product dto object
     *
     * @param productDto      the product dto
     * @param attributeValues a list of saved attribute values
     * @param vendor          the vendor of the product
     * @return the created product
     */
    public Product save(ProductDto productDto, Collection<DynamicAttributeValue<?>> attributeValues, Vendor vendor)
    {
        final Product product = toProduct(productDto, attributeValues, vendor);

        checkCharacteristics(product);

        return productRepository.save(product);
    }

    private void checkCharacteristics(Product product)
    {
        for (DynamicAttribute characteristic : product.getCategory().getCharacteristics())
        {
            if (characteristic.isRequired())
            {
                if (product.getAttributes().stream().noneMatch(dynamicAttributeValue -> Objects.equals(dynamicAttributeValue.getAttributeName(), characteristic.getName())))
                {
                    throw new InvalidDataException(String.format("Product should have a value for characteristic %s", characteristic.getName()));
                }
                final DynamicAttributeValue<?> productCharValue = product.getAttributes().stream().filter(dynamicAttributeValue -> dynamicAttributeValue.getAttributeName().equals(characteristic.getName())).findFirst().orElse(null);
                if (productCharValue != null && productCharValue.getValue() == null)
                {
                    throw new InvalidDataException(String.format("Product should have a value for characteristic %s", characteristic.getName()));
                }
            }
        }
    }

    /**
     * Save a product to the repository
     *
     * @param product the product that needs to be saved
     * @return the saved product
     */
    public Product save(Product product)
    {
        return productRepository.save(product);
    }

    private Product toProduct(ProductDto productDto, Collection<DynamicAttributeValue<?>> attributeValues, Vendor vendor)
    {
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

    /**
     * Find all the products of a given vendor
     *
     * @param vendor the vendor whose products you want
     * @return a list of products from given vendor
     */
    public Collection<Product> findProductsByVendor(Vendor vendor)
    {
        return productRepository.findProductsByVendor(vendor);
    }

    /**
     * Get all the products in the repository
     *
     * @return list of products
     */
    public Collection<Product> findAllProducts()
    {
        return productRepository.findAll();
    }

    /**
     * Edit a product. Same as the save method but checks if the product you want to edit exists. If the product does't exist it will thorw a NotFoundException.
     *
     * @param product the product you want to save
     */
    public void editProduct(Product product)
    {
        checkCharacteristics(product);

        final Product fromRepo = productRepository.findById(product.getId()).orElse(null);
        if (fromRepo == null)
        {
            throw new NotFoundException(String.format("Product with id %s not found", product.getId()));
        }
        if (fromRepo.getVendor() != product.getVendor())
        {
            throw new UnauthorisedException("This vendor does not own this product");
        }


        product.setAttributes(new ArrayList<>(product.getAttributes()));
        dynamicAttributeValueService.deleteNonCategoryAttributes(product);
        productRepository.save(product);
    }

    /**
     * Find a product by id. Throws a not found exception when no product is found
     *
     * @param productId the id of the product
     * @return product with given id
     */
    public Product findProductById(long productId)
    {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null)
        {
            throw new NotFoundException(String.format("Product with id %s not found", productId));
        }
        return product;
    }

    /**
     * Delete a product based on an id
     *
     * @param productId the id of the product that needs to be deleted
     */
    public void delete(long productId)
    {
        productRepository.deleteById(productId);
    }
}
