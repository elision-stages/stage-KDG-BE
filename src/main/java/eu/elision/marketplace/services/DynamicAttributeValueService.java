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

    public DynamicAttributeValueService(DynamicAttributeValueRepository dynamicAttributeValueRepository) {
        this.dynamicAttributeValueRepository = dynamicAttributeValueRepository;
    }


    public void save(Collection<DynamicAttributeValue<?>> collectionToSave) {
        dynamicAttributeValueRepository.saveAll(collectionToSave);
    }

    public void save(DynamicAttributeValue<?> dynamicAttributeValue) {
        dynamicAttributeValueRepository.save(dynamicAttributeValue);
    }

    public void deleteNonCategoryAttributes(Product product) {
        for (DynamicAttributeValue<?> attribute : product.getAttributes()) {
            if (product.getCategory().getCharacteristics().stream().noneMatch(dynamicAttribute -> Objects.equals(dynamicAttribute.getName(), attribute.getAttributeName()))) {
                dynamicAttributeValueRepository.delete(attribute);
            }
        }
        product.removeNonCategoryAttributes();
    }
}
