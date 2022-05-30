package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.logic.services.product.DynamicAttributeService;
import eu.elision.marketplace.repositories.DynamicAttributeRepository;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import eu.elision.marketplace.web.webexceptions.InvalidDataException;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static eu.elision.marketplace.domain.product.category.attributes.Type.DECIMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
        DynamicAttributeDto dynamicAttributeDto = new DynamicAttributeDto(name1, RandomUtils.nextBoolean(), DECIMAL);
        final String name2 = RandomStringUtils.randomAlphabetic(5);
        DynamicAttributeDto dynamicAttributeDto2 = new DynamicAttributeDto(name2, RandomUtils.nextBoolean(), Type.STRING);
        final String name3 = RandomStringUtils.randomAlphabetic(5);
        DynamicAttributeDto dynamicAttributeDto3 = new DynamicAttributeDto(name3, RandomUtils.nextBoolean(), DECIMAL);

        Category category = new Category();

        assertThat(dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto, category).getName()).isEqualTo(name1);
        assertThat(dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto2, category).getName()).isEqualTo(name2);
        assertThat(dynamicAttributeService.toDynamicAttribute(dynamicAttributeDto3, category).getName()).isEqualTo(name3);
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
        dynamicAttribute.setType(DECIMAL);
        when(dynamicAttributeRepository.findDynamicAttributeByName(name)).thenReturn(dynamicAttribute);

        AttributeValue<String, String> attributeValue = new AttributeValue<>();
        attributeValue.setAttributeName(name);
        attributeValue.setValue(String.valueOf(RandomUtils.nextInt()));

        assertThat(dynamicAttributeService.getSavedAttributes(List.of(attributeValue))).hasSize(1);
    }

    @Test
    void testSavedAttributesEnum() {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        final String name = RandomStringUtils.randomAlphabetic(5);
        dynamicAttribute.setName(name);
        dynamicAttribute.setType(Type.STRING);
        final String value = RandomStringUtils.randomAlphabetic(5);
        when(dynamicAttributeRepository.findDynamicAttributeByName(name)).thenReturn(dynamicAttribute);

        AttributeValue<String, String> attributeValue = new AttributeValue<>();
        attributeValue.setAttributeName(name);
        attributeValue.setValue(value);

        assertThat(dynamicAttributeService.getSavedAttributes(List.of(attributeValue))).hasSize(1);
    }

    @Test
    void testRenewAttributesDoubleAttribute() {
        Type[] types = {Type.STRING, Type.DECIMAL, Type.DECIMAL, Type.INTEGER};
        final Category category = new Category();
        category.setId(RandomUtils.nextLong());
        DynamicAttribute dynamicAttribute = new DynamicAttribute(RandomUtils.nextLong(), RandomStringUtils.randomAlphabetic(5), RandomUtils.nextBoolean(), types[RandomUtils.nextInt(0, 4)], category);

        when(dynamicAttributeRepository.findDynamicAttributeByName(dynamicAttribute.getName())).thenReturn(dynamicAttribute);

        CategoryDto editCategoryDto = new CategoryDto(category.getId(), dynamicAttribute.getName(), 0L, new HashSet<>(List.of(new DynamicAttributeDto(dynamicAttribute.getName(), dynamicAttribute.isRequired(), dynamicAttribute.getType()), new DynamicAttributeDto(dynamicAttribute.getName(), !dynamicAttribute.isRequired(), dynamicAttribute.getType()))) {
        });
        Exception exception = assertThrows(InvalidDataException.class, () -> dynamicAttributeService.renewAttributes(editCategoryDto, category));

        assertThat(exception.getMessage()).contains(dynamicAttribute.getName());
    }

    @Test
    void testRenewAttributes2IdenticalCharacteristics() {
        Type[] types = {Type.STRING, Type.DECIMAL, Type.DECIMAL, Type.INTEGER};
        final Category category = new Category();
        category.setId(RandomUtils.nextLong());
        DynamicAttribute dynamicAttribute = new DynamicAttribute(RandomUtils.nextLong(), RandomStringUtils.randomAlphabetic(5), RandomUtils.nextBoolean(), types[RandomUtils.nextInt(0, 4)], category);

        when(dynamicAttributeRepository.findDynamicAttributeByName(dynamicAttribute.getName())).thenReturn(dynamicAttribute);
        when(dynamicAttributeRepository.saveAll(any())).thenReturn(List.of(dynamicAttribute));

        HashSet<DynamicAttributeDto> hashSet = new HashSet<>();
        hashSet.add(new DynamicAttributeDto(dynamicAttribute.getName(), dynamicAttribute.isRequired(), dynamicAttribute.getType()));
        hashSet.add(new DynamicAttributeDto(dynamicAttribute.getName(), dynamicAttribute.isRequired(), dynamicAttribute.getType()));

        CategoryDto editCategoryDto = new CategoryDto(category.getId(), dynamicAttribute.getName(), 0L, hashSet);

        final Collection<DynamicAttribute> renewed = dynamicAttributeService.renewAttributes(editCategoryDto, category);
        assertThat(renewed).hasSize(1);
    }

}