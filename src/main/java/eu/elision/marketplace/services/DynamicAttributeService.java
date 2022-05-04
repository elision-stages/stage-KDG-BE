package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickList;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.domain.product.category.attributes.value.*;
import eu.elision.marketplace.repositories.DynamicAttributeRepository;
import eu.elision.marketplace.web.dtos.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.Pair;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class DynamicAttributeService {

    private final DynamicAttributeRepository dynamicAttributeRepository;
    Logger logger = LoggerFactory.getLogger(DynamicAttributeService.class);

    public DynamicAttributeService(DynamicAttributeRepository dynamicAttributeRepository) {
        this.dynamicAttributeRepository = dynamicAttributeRepository;
    }

    public Collection<DynamicAttributeValue<?>> getSavedAttributes(Collection<Pair<String, String>> attributes) {
        Collection<DynamicAttributeValue<?>> dynamicAttributeValues = new ArrayList<>();

        for (Pair<String, String> attribute : attributes) {
            DynamicAttribute dynamicAttribute = dynamicAttributeRepository.findDynamicAttributeByName(attribute.getAttributeName());
            if (dynamicAttribute == null)
                throw new NotFoundException(String.format("Attribute with name %s not found", attribute.getAttributeName()));

            switch (dynamicAttribute.getType()) {
                case BOOL ->
                        dynamicAttributeValues.add(new DynamicAttributeBoolValue(attribute.getAttributeName(), Boolean.valueOf(attribute.getAttributeValue())));
                case DECIMAL ->
                        dynamicAttributeValues.add(new DynamicAttributeDoubleValue(attribute.getAttributeName(), Double.parseDouble(attribute.getAttributeValue())));
                case INTEGER ->
                        dynamicAttributeValues.add(new DynamicAttributeIntValue(attribute.getAttributeName(), Integer.parseInt(attribute.getAttributeValue())));
                case ENUMERATION -> {
                    if (dynamicAttribute.getEnumList().getItems().stream().noneMatch(pickListItem -> Objects.equals(pickListItem.getValue(), attribute.getAttributeValue())))
                        throw new NotFoundException(String.format("Value %s is not found in enum %s", attribute.getAttributeValue(), dynamicAttribute.getName()));

                    dynamicAttributeValues.add(new DynamicAttributeEnumValue(attribute.getAttributeName(), attribute.getAttributeValue()));
                }

            }
        }
        return dynamicAttributeValues;
    }

    public void save(DynamicAttribute dynamicAttribute) {
        dynamicAttributeRepository.save(dynamicAttribute);
    }

    public DynamicAttribute toDynamicAttribute(DynamicAttributeDto dynamicAttributeDto) {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        dynamicAttribute.setName(dynamicAttributeDto.name());
        dynamicAttribute.setRequired(dynamicAttributeDto.required());
        dynamicAttribute.setType(dynamicAttributeDto.type());

        PickList pickList = new PickList();
        if (dynamicAttribute.getType() == Type.ENUMERATION) {
            for (String enumValue : dynamicAttributeDto.enumValues()) {
                pickList.getItems().add(new PickListItem(enumValue));
            }
        } else if (dynamicAttributeDto.enumValues() != null && !dynamicAttributeDto.enumValues().isEmpty())
            logger.warn("Values in enum list ignored because of type {}", dynamicAttributeDto.type());

        return dynamicAttribute;
    }
}
