package eu.elision.marketplace.logic.services.product;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickList;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.domain.product.category.attributes.value.*;
import eu.elision.marketplace.repositories.DynamicAttributeRepository;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Service for dynamic attributes
 */
@Service
public class DynamicAttributeService
{

    private final DynamicAttributeRepository dynamicAttributeRepository;
    Logger logger = LoggerFactory.getLogger(DynamicAttributeService.class);

    public DynamicAttributeService(DynamicAttributeRepository dynamicAttributeRepository) {
        this.dynamicAttributeRepository = dynamicAttributeRepository;
    }

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
                case ENUMERATION -> {
                    if (dynamicAttribute.getEnumList().getItems().stream().noneMatch(pickListItem -> Objects.equals(pickListItem.getValue(), attribute.getValue())))
                        throw new NotFoundException(String.format("Value %s is not found in enum %s", attribute.getValue(), dynamicAttribute.getName()));

                    dynamicAttributeValues.add(new DynamicAttributeEnumValue(attribute.getAttributeName(), attribute.getValue()));
                }

            }
        }
        return dynamicAttributeValues;
    }

    public DynamicAttribute save(DynamicAttribute dynamicAttribute) {
        if (dynamicAttributeRepository.existsByName(dynamicAttribute.getName())) {
            logger.warn("Dynamic attribute with name {} already exists, not saving instance", dynamicAttribute.getName());
        }
        return dynamicAttributeRepository.save(dynamicAttribute);
    }

    public DynamicAttribute toDynamicAttribute(DynamicAttributeDto dynamicAttributeDto) {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        dynamicAttribute.setName(dynamicAttributeDto.name());
        dynamicAttribute.setRequired(dynamicAttributeDto.required());
        dynamicAttribute.setType(dynamicAttributeDto.type());

        PickList pickList = new PickList();
        if (pickList.getItems() == null) pickList.setItems(new ArrayList<>());

        if (dynamicAttribute.getType() == Type.ENUMERATION) {
            for (String enumValue : dynamicAttributeDto.enumValues())
                pickList.getItems().add(new PickListItem(enumValue));
            dynamicAttribute.setEnumList(pickList);
        } else if (dynamicAttributeDto.enumValues() != null && !dynamicAttributeDto.enumValues().isEmpty())
            logger.warn("Values in enum list ignored because of type {}", dynamicAttributeDto.type());

        return dynamicAttribute;
    }

    public Collection<DynamicAttribute> toDynamicAttributes(Collection<DynamicAttributeDto> dynamicAttributeDtos) {
        return dynamicAttributeDtos.stream().map(this::toDynamicAttribute).toList();
    }
}
