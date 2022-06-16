package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeIntValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.logic.services.product.DynamicAttributeValueService;
import eu.elision.marketplace.repositories.DynamicAttributeValueRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class DynamicAttributeValueServiceTest
{

    @InjectMocks
    DynamicAttributeValueService dynamicAttributeValueService;
    @Mock
    DynamicAttributeValueRepository dynamicAttributeValueRepository;

    @Test
    void save()
    {
        final String attributeName = RandomStringUtils.randomAlphabetic(5);
        final int value = RandomUtils.nextInt();
        DynamicAttributeValue<Integer> dynamicAttributeValue = new DynamicAttributeIntValue(attributeName, value);

        when(dynamicAttributeValueRepository.save(dynamicAttributeValue)).thenReturn(dynamicAttributeValue);

        assertThat(dynamicAttributeValueService.save(dynamicAttributeValue)).isEqualTo(dynamicAttributeValue);
    }

    @Test
    void saveCollection()
    {
        final String attributeName = RandomStringUtils.randomAlphabetic(5);
        final int value = RandomUtils.nextInt();
        DynamicAttributeValue<Integer> dynamicAttributeValue = new DynamicAttributeIntValue(attributeName, value);

        final String attributeName1 = RandomStringUtils.randomAlphabetic(5);
        final int value1 = RandomUtils.nextInt();
        DynamicAttributeValue<Integer> dynamicAttributeValue1 = new DynamicAttributeIntValue(attributeName1, value1);
        final List<DynamicAttributeValue<?>> attributeValues = List.of(dynamicAttributeValue, dynamicAttributeValue1);

        when(dynamicAttributeValueRepository.saveAll(attributeValues)).thenReturn(attributeValues);

        assertThat(dynamicAttributeValueService.save(attributeValues)).isEqualTo(attributeValues);
    }

    @Test
    void deleteNonCategoryAttributes()
    {
        Category category = new Category();
        DynamicAttribute da = new DynamicAttribute();
        da.setCategory(category);
        da.setName(RandomStringUtils.randomAlphabetic(5));
        category.getCharacteristics().add(da);

        da = new DynamicAttribute();
        da.setCategory(category);
        da.setName(RandomStringUtils.randomAlphabetic(5));
        category.getCharacteristics().add(da);

        int value = RandomUtils.nextInt();
        DynamicAttributeValue<Integer> dynamicAttributeValue = new DynamicAttributeIntValue(da.getName(), value);

        value = RandomUtils.nextInt();
        DynamicAttributeValue<Integer> dynamicAttributeValue1 = new DynamicAttributeIntValue(RandomStringUtils.randomAlphabetic(5), value);
        Product product = new Product();
        product.getAttributes().addAll(List.of(dynamicAttributeValue1, dynamicAttributeValue));
        product.setCategory(category);

        dynamicAttributeValueService.deleteNonCategoryAttributes(product);

        assertThat(product.getAttributes()).hasSize(1);
    }
}