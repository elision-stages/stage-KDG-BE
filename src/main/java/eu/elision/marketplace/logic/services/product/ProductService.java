package eu.elision.marketplace.logic.services.product;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.exceptions.InvalidDataException;
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.exceptions.UnauthorisedException;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.repositories.ProductRepository;
import eu.elision.marketplace.web.dtos.product.ProductDto;
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
     * @param productRepository            the repository that the service has to use
     * @param dynamicAttributeValueService the attribute value service that the service has to use
     */
    public ProductService(ProductRepository productRepository, DynamicAttributeValueService dynamicAttributeValueService)
    {
        this.productRepository = productRepository;
        this.dynamicAttributeValueService = dynamicAttributeValueService;
    }

    //------------------------------------ save / edit / delete products

    /**
     * Save a product dto object
     *
     * @param productDto      the product dto
     * @param attributeValues a list of saved attribute values
     * @param vendor          the vendor of the product
     * @param category        the category of the product
     * @return the created product
     */
    public Product save(ProductDto productDto, Collection<DynamicAttributeValue<?>> attributeValues, Vendor vendor, Category category)
    {
        final Product product = Mapper.toProduct(productDto, attributeValues, vendor, category);
        checkCharacteristics(product);

        return productRepository.save(product);
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

    /**
     * Edit a product. Same as the save method but checks if the product you want to edit exists. If the product doesn't exist it will throw a NotFoundException.
     *
     * @param product the product you want to save
     * @return The saved product
     */
    public Product editProduct(Product product)
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
        return productRepository.save(product);
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

    /**
     * Delete a product
     *
     * @param productId   the id of the product that needs to be deleted
     * @param vendorEmail the email of the vendor that wants to delete the product
     */
    public void deleteProduct(long productId, String vendorEmail)
    {
        Product toBeDeleted = findProductById(productId);
        if (!Objects.equals(toBeDeleted.getVendor().getEmail(), vendorEmail))
            throw new UnauthorisedException(String.format("Vendor with id %s is not allowed to delete product with email %s", productId, vendorEmail));
        productRepository.delete(toBeDeleted);
    }


    //---------------------------------------------- find products

    /**
     * Find all the products of a given vendor
     *
     * @param vendorEmail the email of the vendor whose products you want
     * @return a list of products from given vendor
     */
    public Collection<Product> findProductsByVendorEmail(String vendorEmail)
    {
        return productRepository.findByVendorEmail(vendorEmail);
    }

    /**
     * Find all products of given vendor
     *
     * @param id the id of the vendor
     * @return the products of given vendor
     */
    public Collection<Product> findProductsByVendorId(long id)
    {
        return productRepository.findAllByVendorId(id);
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

    //-------------------------------------------------- private methods
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
}
