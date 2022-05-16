package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.repositories.DynamicAttributeValueRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * Service for dynamic attribute values
 */
@Service
public class DynamicAttributeValueService {
    private final DynamicAttributeValueRepository dynamicAttributeValueRepository;

    /**
     * Public constructor
     *
     * @param dynamicAttributeValueRepository the repository that the service uses
     */
    public DynamicAttributeValueService(DynamicAttributeValueRepository dynamicAttributeValueRepository) {
        this.dynamicAttributeValueRepository = dynamicAttributeValueRepository;
    }


    /**
     * Saves a list of dynamic attribute values
     *
     * @param collectionToSave the list that needs to be saved
     */
    public void save(Collection<DynamicAttributeValue<?>> collectionToSave) {
        dynamicAttributeValueRepository.saveAll(collectionToSave);
    }

    /**
     * Save a dynamic attribute value
     *
     * @param dynamicAttributeValue the dynamic attribute value that needs to be saved
     */
    public void save(DynamicAttributeValue<?> dynamicAttributeValue) {
        dynamicAttributeValueRepository.save(dynamicAttributeValue);
    }

    /**
     * Delete the attributes from a product both from the product itself and from the repository
     *
     * @param product
     */
    public void deleteNonCategoryAttributes(Product product) {
        for (DynamicAttributeValue<?> attribute : product.getAttributes()) {
            if (product.getCategory().getCharacteristics().stream().noneMatch(dynamicAttribute -> Objects.equals(dynamicAttribute.getName(), attribute.getAttributeName()))) {
                dynamicAttributeValueRepository.delete(attribute);
            }
        }
        product.removeNonCategoryAttributes();
    }

    public Collection<DynamicAttributeValue<?>> findAll() {
        return dynamicAttributeValueRepository.findAll();
    }
}
