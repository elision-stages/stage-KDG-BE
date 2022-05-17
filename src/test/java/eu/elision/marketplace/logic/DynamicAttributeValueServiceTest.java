package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeIntValue;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.logic.services.product.DynamicAttributeValueService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DynamicAttributeValueServiceTest {

    @Autowired
    DynamicAttributeValueService dynamicAttributeValueService;

    @Test
    void save() {
        final int initSize = dynamicAttributeValueService.findAll().size();
        final String attributeName = RandomStringUtils.randomAlphabetic(5);
        final int value = RandomUtils.nextInt();
        DynamicAttributeValue<Integer> dynamicAttributeValue = new DynamicAttributeIntValue(attributeName, value);

        dynamicAttributeValueService.save(dynamicAttributeValue);
        assertThat(dynamicAttributeValueService.findAll()).hasSize(initSize + 1);
    }

    @Test
    void saveCollection() {
        final int initSize = dynamicAttributeValueService.findAll().size();

        final String attributeName = RandomStringUtils.randomAlphabetic(5);
        final int value = RandomUtils.nextInt();
        DynamicAttributeValue<Integer> dynamicAttributeValue = new DynamicAttributeIntValue(attributeName, value);

        final String attributeName1 = RandomStringUtils.randomAlphabetic(5);
        final int value1 = RandomUtils.nextInt();
        DynamicAttributeValue<Integer> dynamicAttributeValue1 = new DynamicAttributeIntValue(attributeName1, value1);

        dynamicAttributeValueService.save(List.of(dynamicAttributeValue, dynamicAttributeValue1));
        assertThat(dynamicAttributeValueService.findAll()).hasSize(initSize + 2);
    }

    @Test
    void deleteNonCategoryAttributes() {
        Category category = new Category();
        DynamicAttribute da = new DynamicAttribute();
        da.setCategory(category);
        final String name = RandomStringUtils.randomAlphabetic(5);
        da.setName(name);
        category.getCharacteristics().add(da);
        da = new DynamicAttribute();
        da.setCategory(category);
        final String name1 = RandomStringUtils.randomAlphabetic(5);
        da.setName(name1);

        final int value = RandomUtils.nextInt();
        DynamicAttributeValue<Integer> dynamicAttributeValue = new DynamicAttributeIntValue(name, value);

        final int value1 = RandomUtils.nextInt();
        DynamicAttributeValue<Integer> dynamicAttributeValue1 = new DynamicAttributeIntValue(RandomStringUtils.randomAlphabetic(5), value1);
        Product product = new Product();
        product.getAttributes().addAll(List.of(dynamicAttributeValue1, dynamicAttributeValue));
        product.setCategory(category);

        dynamicAttributeValueService.save(List.of(dynamicAttributeValue, dynamicAttributeValue1));

        dynamicAttributeValueService.deleteNonCategoryAttributes(product);

        assertThat(product.getAttributes()).hasSize(1);
    }
}