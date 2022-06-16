package eu.elision.marketplace.logic.services.product;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.repositories.DynamicAttributeValueRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service for dynamic attribute values
 */
@Service
public class DynamicAttributeValueService
{
    private final DynamicAttributeValueRepository dynamicAttributeValueRepository;

    /**
     * Public constructor
     *
     * @param dynamicAttributeValueRepository the repository that the service has to use
     */
    public DynamicAttributeValueService(DynamicAttributeValueRepository dynamicAttributeValueRepository)
    {
        this.dynamicAttributeValueRepository = dynamicAttributeValueRepository;
    }

    /**
     * Saves a list of dynamic attribute values
     *
     * @param collectionToSave the list that needs to be saved
     * @return the saved collection of dynamic attributes
     */
    public Collection<DynamicAttributeValue<?>> save(Collection<DynamicAttributeValue<?>> collectionToSave)
    {
        return dynamicAttributeValueRepository.saveAll(collectionToSave);
    }

    /**
     * Save a dynamic attribute value
     *
     * @param dynamicAttributeValue the dynamic attribute value that needs to be saved
     * @return the saved dynamic attribute value
     */
    public DynamicAttributeValue<?> save(DynamicAttributeValue<?> dynamicAttributeValue)
    {
        return dynamicAttributeValueRepository.save(dynamicAttributeValue);
    }

    /**
     * Delete the attributes from a product both from the product itself and from the repository. Necessary for when a product changes category
     *
     * @param product the product where the attributes need to be updated
     */
    public void deleteNonCategoryAttributes(Product product)
    {
        for (DynamicAttributeValue<?> attribute : product.getAttributes())
        {
            if (product.getCategory().getCharacteristics().stream().noneMatch(dynamicAttribute -> dynamicAttribute.getName().equals(attribute.getAttributeName())))
                dynamicAttributeValueRepository.delete(attribute);
        }

        product.removeNonCategoryAttributes();
    }

    /**
     * Find all the attribute values, only used for testing
     *
     * @return returns a list of all the attribute values
     */
    public Collection<DynamicAttributeValue<?>> findAll()
    {
        return dynamicAttributeValueRepository.findAll();
    }

}
