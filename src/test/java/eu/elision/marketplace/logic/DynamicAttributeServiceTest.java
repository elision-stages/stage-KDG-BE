package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickList;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.logic.services.product.DynamicAttributeService;
import eu.elision.marketplace.repositories.DynamicAttributeRepository;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class DynamicAttributeServiceTest {

    @InjectMocks
    private DynamicAttributeService dynamicAttributeService;
    @Mock
    private DynamicAttributeRepository dynamicAttributeRepository;

    @Test
    void toDynamicAttributeTest() {
        final String name1 = RandomStringUtils.randomAlphabetic(5);
        DynamicAttributeDto dynamicAttributeDto = new DynamicAttributeDto(name1, RandomUtils.nextBoolean(), Type.DECIMAL, null);
        final String name2 = RandomStringUtils.randomAlphabetic(5);
        DynamicAttributeDto dynamicAttributeDto2 = new DynamicAttributeDto(name2, RandomUtils.nextBoolean(), Type.ENUMERATION, new ArrayList<>(List.of(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5))));
        final String name3 = RandomStringUtils.randomAlphabetic(5);
        DynamicAttributeDto dynamicAttributeDto3 = new DynamicAttributeDto(name3, RandomUtils.nextBoolean(), Type.DECIMAL, new ArrayList<>(List.of(RandomStringUtils.randomAlphabetic(4))));

        assertThat(dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto).getName()).isEqualTo(name1);
        assertThat(dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto2).getName()).isEqualTo(name2);
        assertThat(dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto3).getName()).isEqualTo(name3);
    }

    @Test
    void testNotFoundSavedAttributes() {
        AttributeValue<String, String> attributeValue = new AttributeValue<>();
        final String attributeName = RandomStringUtils.randomAlphabetic(5);
        attributeValue.setAttributeName(attributeName);

        final List<AttributeValue<String, String>> attributeValue1 = List.of(attributeValue);
        Exception exception = assertThrows(NotFoundException.class, () -> dynamicAttributeService.getSavedAttributes(attributeValue1));

        assertThat(exception.getMessage()).isEqualTo(String.format("Attribute with name %s not found", attributeName));
    }

    @Test
    void testSavedAttributesBool() {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        final String name = RandomStringUtils.randomAlphabetic(5);
        dynamicAttribute.setName(name);
        dynamicAttribute.setType(Type.BOOL);
        when(dynamicAttributeRepository.findDynamicAttributeByName(name)).thenReturn(dynamicAttribute);

        AttributeValue<String, String> attributeValue = new AttributeValue<>();
        attributeValue.setAttributeName(name);
        attributeValue.setValue(String.valueOf(RandomUtils.nextBoolean()));

        assertThat(dynamicAttributeService.getSavedAttributes(List.of(attributeValue))).hasSize(1);
    }

    @Test
    void testSavedAttributesDecimal() {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        final String name = RandomStringUtils.randomAlphabetic(5);
        dynamicAttribute.setName(name);
        dynamicAttribute.setType(Type.DECIMAL);
        when(dynamicAttributeRepository.findDynamicAttributeByName(name)).thenReturn(dynamicAttribute);

        AttributeValue<String, String> attributeValue = new AttributeValue<>();
        attributeValue.setAttributeName(name);
        attributeValue.setValue(String.valueOf(RandomUtils.nextInt()));

        assertThat(dynamicAttributeService.getSavedAttributes(List.of(attributeValue))).hasSize(1);
    }

    @Test
    void testSavedAttributesEnumFails() {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        final String name = RandomStringUtils.randomAlphabetic(5);
        dynamicAttribute.setName(name);
        dynamicAttribute.setType(Type.ENUMERATION);
        final PickList enumList = new PickList();
        enumList.setItems(List.of(new PickListItem(RandomStringUtils.randomAlphabetic(5))));
        dynamicAttribute.setEnumList(enumList);
        when(dynamicAttributeRepository.findDynamicAttributeByName(name)).thenReturn(dynamicAttribute);

        AttributeValue<String, String> attributeValue = new AttributeValue<>();
        attributeValue.setAttributeName(name);
        attributeValue.setValue(String.valueOf(RandomUtils.nextBoolean()));

        final List<AttributeValue<String, String>> attributeValue1 = List.of(attributeValue);
        assertThrows(NotFoundException.class, () -> dynamicAttributeService.getSavedAttributes(attributeValue1));
    }

    @Test
    void testSavedAttributesEnum() {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        final String name = RandomStringUtils.randomAlphabetic(5);
        dynamicAttribute.setName(name);
        dynamicAttribute.setType(Type.ENUMERATION);
        final PickList enumList = new PickList();
        final String value = RandomStringUtils.randomAlphabetic(5);
        enumList.setItems(List.of(new PickListItem(value)));
        dynamicAttribute.setEnumList(enumList);
        when(dynamicAttributeRepository.findDynamicAttributeByName(name)).thenReturn(dynamicAttribute);

        AttributeValue<String, String> attributeValue = new AttributeValue<>();
        attributeValue.setAttributeName(name);
        attributeValue.setValue(value);

        assertThat(dynamicAttributeService.getSavedAttributes(List.of(attributeValue))).hasSize(1);
    }

}