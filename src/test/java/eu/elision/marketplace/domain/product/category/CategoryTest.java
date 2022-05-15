package eu.elision.marketplace.domain.product.category;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickList;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
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
    @Transactional
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
        pickList.setItems(new ArrayList<>(List.of(new PickListItem(RandomUtils.nextLong(1, 100), RandomStringUtils.random(1)), new PickListItem(RandomUtils.nextLong(1, 100), RandomStringUtils.random(1)), new PickListItem(RandomUtils.nextLong(1, 100), RandomStringUtils.random(1)))));
        sub1.setName(subName);
        DynamicAttribute characteristic1 = new DynamicAttribute(RandomUtils.nextLong(1, 100), firstName, true, Type.DECIMAL, null, sub1);
        DynamicAttribute characteristic2 = new DynamicAttribute(RandomUtils.nextLong(1, 100), secondName, false, Type.INTEGER, null, sub1);
        DynamicAttribute characteristic3 = new DynamicAttribute(RandomUtils.nextLong(1, 100), thirdName, true, Type.ENUMERATION, pickList, sub1);

        sub1.setParent(category);
        final Category actual = sub1;

        assertThat(actual.getName()).isEqualTo(subName);
        assertThat(characteristic1.getName()).isEqualTo(firstName);
        assertThat(characteristic1.isRequired()).isTrue();
        assertThat(characteristic1.getType()).isEqualTo(Type.DECIMAL);
        assertThat(characteristic1.getEnumList()).isNull();

        assertThat(characteristic2.getName()).isEqualTo(secondName);
        assertThat(characteristic2.isRequired()).isFalse();
        assertThat(characteristic2.getType()).isEqualTo(Type.INTEGER);
        assertThat(characteristic2.getEnumList()).isNull();

        assertThat(characteristic3.getName()).isEqualTo(thirdName);
        assertThat(characteristic3.isRequired()).isTrue();
        assertThat(characteristic3.getType()).isEqualTo(Type.ENUMERATION);
        assertThat(characteristic3.getEnumList().getItems()).hasSize(3);
    }

    @Test
    void getSetId() {
        Category category = new Category();
        final long id = RandomUtils.nextLong();
        category.setId(id);

        assertThat(category.getId()).isEqualTo(id);
    }
}