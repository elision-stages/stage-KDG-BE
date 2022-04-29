package eu.elision.marketplace.domain.product.category;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickList;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest
{

    @Test
    void getSetName()
    {
        Category category = new Category();
        final String name = RandomStringUtils.random(5);
        category.setName(name);

        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    void getSubCategories()
    {
        Category category = new Category();

        final String subName = RandomStringUtils.random(5);
        final String firstName = RandomStringUtils.random(5);
        final String secondName = RandomStringUtils.random(5);
        final String thirdName = RandomStringUtils.random(5);

        Category sub1 = new Category();
        PickList pickList = new PickList();
        pickList.setCode(String.valueOf(RandomUtils.nextInt(1, 1000)));
        pickList.setItems(new ArrayList<>(List.of(new PickListItem(RandomUtils.nextLong(1, 100), RandomStringUtils.random(1)), new PickListItem(RandomUtils.nextLong(1,100), RandomStringUtils.random(1)), new PickListItem(RandomUtils.nextLong(1,100), RandomStringUtils.random(1)))));
        sub1.setName(subName);
        sub1.getCharacteristics().add(new DynamicAttribute(RandomUtils.nextLong(1, 100), firstName, true, Type.DECIMAL, null));
        sub1.getCharacteristics().add(new DynamicAttribute(RandomUtils.nextLong(1, 100), secondName, false, Type.INTEGER, null));
        sub1.getCharacteristics().add(new DynamicAttribute(RandomUtils.nextLong(1, 100), thirdName, true, Type.ENUMERATION, pickList));

        category.getSubCategories().add(sub1);
        final Category actual = category.getSubCategories().get(0);

        assertThat(category.getSubCategories()).hasSize(1);
        assertThat(actual).isEqualTo(sub1);

        assertThat(actual.getName()).isEqualTo(subName);
        assertThat(actual.getCharacteristics().get(0).getName()).isEqualTo(firstName);
        assertThat(actual.getCharacteristics().get(0).isRequired()).isTrue();
        assertThat(actual.getCharacteristics().get(0).getType()).isEqualTo(Type.DECIMAL);
        assertThat(actual.getCharacteristics().get(0).getEnumList()).isNull();

        assertThat(actual.getCharacteristics().get(1).getName()).isEqualTo(secondName);
        assertThat(actual.getCharacteristics().get(1).isRequired()).isFalse();
        assertThat(actual.getCharacteristics().get(1).getType()).isEqualTo(Type.INTEGER);
        assertThat(actual.getCharacteristics().get(1).getEnumList()).isNull();

        assertThat(actual.getCharacteristics().get(2).getName()).isEqualTo(thirdName);
        assertThat(actual.getCharacteristics().get(2).isRequired()).isTrue();
        assertThat(actual.getCharacteristics().get(2).getType()).isEqualTo(Type.ENUMERATION);
        assertThat(actual.getCharacteristics().get(2).getEnumList().getItems()).hasSize(3);
    }

    @Test
    void getSetId() {
        Category category = new Category();
        final long id = RandomUtils.nextLong();
        category.setId(id);

        assertThat(category.getId()).isEqualTo(id);
    }
}