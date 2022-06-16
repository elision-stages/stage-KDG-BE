package eu.elision.marketplace.logic.services.product;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.value.*;
import eu.elision.marketplace.exceptions.InvalidDataException;
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.repositories.DynamicAttributeRepository;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Service for dynamic attributes
 */
@Service
public class DynamicAttributeService
{
    private final DynamicAttributeRepository dynamicAttributeRepository;

    /**
     * Public constructor
     *
     * @param dynamicAttributeRepository the dynamic attribute repository that the service needs to use
     */
    public DynamicAttributeService(DynamicAttributeRepository dynamicAttributeRepository)
    {
        this.dynamicAttributeRepository = dynamicAttributeRepository;
    }

    /**
     * Get the values of specific attributes
     *
     * @param attributes Collection of AttributeValue you want to retrieve the values of
     * @param categoryId the id of the category
     * @return List of DynamicAttributeValue
     */
    public Collection<DynamicAttributeValue<?>> getSavedAttributes(Collection<AttributeValue<String, String>> attributes, long categoryId)
    {
        Collection<DynamicAttributeValue<?>> dynamicAttributeValues = new ArrayList<>();

        for (AttributeValue<String, String> attribute : attributes)
        {
            DynamicAttribute dynamicAttribute = dynamicAttributeRepository.findDynamicAttributeByNameAndCategory(attribute.getAttributeName(), categoryId);
            if (dynamicAttribute == null)
                throw new NotFoundException(String.format("Attribute with name %s not found", attribute.getAttributeName()));

            switch (dynamicAttribute.getType())
            {
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
    public DynamicAttribute save(DynamicAttribute dynamicAttribute)
    {
        if (dynamicAttributeRepository.existsByName(dynamicAttribute.getName()))
        {
            dynamicAttribute.setId(dynamicAttributeRepository.findDynamicAttributeByName(dynamicAttribute.getName()).getId());
            dynamicAttributeRepository.save(dynamicAttribute);
        }
        return dynamicAttributeRepository.save(dynamicAttribute);
    }

    /**
     * Delete the previous attributes of a category and return the new saved ones
     *
     * @param editCategoryDto the dto with the category id and new characteristics of a category
     * @param category        the category that needs the attributes to be reset
     * @return a collection of the new saved attributes
     */
    public Collection<DynamicAttribute> renewAttributes(CategoryDto editCategoryDto, Category category)
    {
        checkDoubles(editCategoryDto.getCharacteristics());

        final Collection<DynamicAttribute> allByCategoryId = dynamicAttributeRepository.findAllByCategory(category);
        dynamicAttributeRepository.deleteAll(allByCategoryId);

        final Collection<DynamicAttribute> entities = Mapper.toDynamicAttributes(editCategoryDto.getCharacteristics(), category);
        return dynamicAttributeRepository.saveAll(entities);
    }

    private void checkDoubles(Collection<DynamicAttributeDto> characteristics)
    {
        for (DynamicAttributeDto characteristic : characteristics)
        {
            if (characteristics.stream().filter(dynamicAttributeDto -> dynamicAttributeDto.getName().equals(characteristic.getName())).count() > 1L)
                throw new InvalidDataException(String.format("Characteristics have duplicate name %s", characteristic.getName()));
        }
    }
}
