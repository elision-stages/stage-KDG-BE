package eu.elision.marketplace.domain.product.category;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickList;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    void getSetName() {
        Category category = new Category();
        final String name = HelperMethods.randomString(5);
        category.setName(name);

        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    void getSubCategories() {
        Category category = new Category();

        final String subName = HelperMethods.randomString(5);
        final String firstName = HelperMethods.randomString(5);
        final String secondName = HelperMethods.randomString(5);
        final String thirdName = HelperMethods.randomString(5);

        Category sub1 = new Category();
        PickList pickList = new PickList();
        pickList.setCode(String.valueOf(HelperMethods.randomInt()));
        pickList.setItems(new ArrayList<>(List.of(new PickListItem(HelperMethods.randomString(1)), new PickListItem(HelperMethods.randomString(1)), new PickListItem(HelperMethods.randomString(1)))));
        sub1.setName(subName);
        sub1.getCharacteristics().add(new DynamicAttribute(firstName, true, Type.DECIMAL, null));
        sub1.getCharacteristics().add(new DynamicAttribute(secondName, false, Type.INTEGER, null));
        sub1.getCharacteristics().add(new DynamicAttribute(thirdName, true, Type.ENUMERATION, pickList));

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
}