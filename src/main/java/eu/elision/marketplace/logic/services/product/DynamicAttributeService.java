package eu.elision.marketplace.logic.services.product;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.value.*;
import eu.elision.marketplace.repositories.DynamicAttributeRepository;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import eu.elision.marketplace.web.webexceptions.InvalidDataException;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Service for dynamic attributes
 */
@Service
public class DynamicAttributeService {

    private final DynamicAttributeRepository dynamicAttributeRepository;
    Logger logger = LoggerFactory.getLogger(DynamicAttributeService.class);

    /**
     * DynamicAttributeService
     *
     * @param dynamicAttributeRepository DynamicAttributeRepository
     */
    public DynamicAttributeService(DynamicAttributeRepository dynamicAttributeRepository) {
        this.dynamicAttributeRepository = dynamicAttributeRepository;
    }

    /**
     * Get the values of specific attributes
     *
     * @param attributes Collection of AttributeValue you want to retrieve the values of
     * @return List of DynamicAttributeValue
     */
    public Collection<DynamicAttributeValue<?>> getSavedAttributes(Collection<AttributeValue<String, String>> attributes) {
        Collection<DynamicAttributeValue<?>> dynamicAttributeValues = new ArrayList<>();

        for (AttributeValue<String, String> attribute : attributes) {
            DynamicAttribute dynamicAttribute = dynamicAttributeRepository.findDynamicAttributeByName(attribute.getAttributeName());
            if (dynamicAttribute == null)
                throw new NotFoundException(String.format("Attribute with name %s not found", attribute.getAttributeName()));

            switch (dynamicAttribute.getType()) {
                case BOOL ->
                        dynamicAttributeValues.add(new DynamicAttributeBoolValue(attribute.getAttributeName(), Boolean.valueOf(attribute.getValue())));
                case DECIMAL ->
                        dynamicAttributeValues.add(new DynamicAttributeDoubleValue(attribute.getAttributeName(), Double.parseDouble(attribute.getValue())));
                case INTEGER ->
                        dynamicAttributeValues.add(new DynamicAttributeIntValue(attribute.getAttributeName(), Integer.parseInt(attribute.getValue())));
                case STRING ->
                        dynamicAttributeValues.add(new DynamicAttributeStringValue(attribute.getAttributeName(), attribute.getValue()));


            }
        }
        return dynamicAttributeValues;
    }

    /**
     * Save a DynamicAttribute
     *
     * @param dynamicAttribute DynamicAttribute to save
     * @return Saved DynamicAttribute
     */
    public DynamicAttribute save(DynamicAttribute dynamicAttribute) {
        if (dynamicAttributeRepository.existsByName(dynamicAttribute.getName())) {
            logger.warn("Dynamic attribute with name {} already exists, not saving instance", dynamicAttribute.getName());
        }
        return dynamicAttributeRepository.save(dynamicAttribute);
    }

    /**
     * Convert a DynamicAttributeDto to a DynamicAttribute
     *
     * @param dynamicAttributeDto DynamicAttributeDto to convert to a DynamicAttribute
     * @param category            the category that needs to be set to the attribute
     * @return The DynamicAttribute
     */
    public DynamicAttribute toDynamicAttribute(DynamicAttributeDto dynamicAttributeDto, Category category) {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        dynamicAttribute.setName(dynamicAttributeDto.name());
        dynamicAttribute.setRequired(dynamicAttributeDto.required());
        dynamicAttribute.setType(dynamicAttributeDto.type());
        dynamicAttribute.setCategory(category);

        return dynamicAttribute;
    }

    /**
     * Convert a collection of DynamicAttributeDto to DynamicAttribute
     *
     * @param dynamicAttributeDtos Collection of DTOs
     * @param category             the category that needs to be set to the attributes
     * @return Collection of DynamicAttribute
     */
    public Collection<DynamicAttribute> toDynamicAttributes(Collection<DynamicAttributeDto> dynamicAttributeDtos, Category category) {
        return dynamicAttributeDtos.stream().map(dynamicAttributeDto -> toDynamicAttribute(dynamicAttributeDto, category)).toList();
    }

    /**
     * Delete the previous attributes of a category and return the new saved ones
     *
     * @param editCategoryDto the dto with the category id and new characteristics of a category
     * @param category        the category that needs the attributes to be reset
     * @return a collection of the new saved attributes
     */
    public Collection<DynamicAttribute> renewAttributes(CategoryDto editCategoryDto, Category category) {
        checkDoubles(editCategoryDto.characteristics());

        final Collection<DynamicAttribute> allByCategoryId = dynamicAttributeRepository.findAllByCategory(category);
        dynamicAttributeRepository.deleteAll(allByCategoryId);

        final Collection<DynamicAttribute> entities = toDynamicAttributes(editCategoryDto.characteristics(), category);
        return dynamicAttributeRepository.saveAll(entities);
    }

    private void checkDoubles(Collection<DynamicAttributeDto> characteristics) {
        for (DynamicAttributeDto characteristic : characteristics) {
            if (characteristics.stream().filter(dynamicAttributeDto -> dynamicAttributeDto.name().equals(characteristic.name())).count() > 1L)
                throw new InvalidDataException(String.format("Characteristics have duplicate name %s", characteristic.name()));
        }
    }
}
