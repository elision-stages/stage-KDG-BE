package eu.elision.marketplace.domain.product.category;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickList;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void getSetName() {
        Category category = new Category();
        category.setName("name");

        assertThat(category.getName()).isEqualTo("name");
    }

    @Test
    void getSubCategories() {
        Category category = new Category();

        Category sub1 = new Category();
        PickList pickList = new PickList();
        pickList.setCode("1");
        pickList.setItems(new ArrayList<>(List.of(new PickListItem("s"), new PickListItem("m"), new PickListItem("l"))));
        sub1.setName("Sub1");
        sub1.getCharacteristics().add(new DynamicAttribute("first", true, Type.DECIMAL, null));
        sub1.getCharacteristics().add(new DynamicAttribute("second", false, Type.INTEGER, null));
        sub1.getCharacteristics().add(new DynamicAttribute("third", true, Type.ENUMMERATION, pickList));

        category.getSubCategories().add(sub1);

        assertThat(category.getSubCategories()).hasSize(1);
        final Category actual = category.getSubCategories().get(0);
        assertThat(actual).isEqualTo(sub1);

        assertThat(actual.getName()).isEqualTo("Sub1");
        assertThat(actual.getCharacteristics().get(0).getName()).isEqualTo("first");
        assertThat(actual.getCharacteristics().get(0).isRequired()).isTrue();
        assertThat(actual.getCharacteristics().get(0).getType()).isEqualTo(Type.DECIMAL);
        assertThat(actual.getCharacteristics().get(0).getEnumList()).isNull();

        assertThat(actual.getCharacteristics().get(1).getName()).isEqualTo("second");
        assertThat(actual.getCharacteristics().get(1).isRequired()).isFalse();
        assertThat(actual.getCharacteristics().get(1).getType()).isEqualTo(Type.INTEGER);
        assertThat(actual.getCharacteristics().get(1).getEnumList()).isNull();

        assertThat(actual.getCharacteristics().get(2).getName()).isEqualTo("third");
        assertThat(actual.getCharacteristics().get(2).isRequired()).isTrue();
        assertThat(actual.getCharacteristics().get(2).getType()).isEqualTo(Type.ENUMMERATION);
        assertThat(actual.getCharacteristics().get(2).getEnumList().getItems()).hasSize(3);
    }
}