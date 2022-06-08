package eu.elision.marketplace.domain.product.category.attributes;

import eu.elision.marketplace.domain.product.category.attributes.picklist.PickList;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PickListTest {

    @Test
    void getSetCode() {
        PickList pickList = new PickList();
        final String code = RandomStringUtils.random(4);

        pickList.setCode(code);
        assertThat(pickList.getCode()).hasToString(code);
    }

    @Test
    void getSetItems() {
        PickList pickList = new PickList();

        pickList.setItems(new ArrayList<>());
        assertThat(pickList.getItems()).isNotNull();
    }

    @Test
    void getSetId() {
        PickList pl = new PickList();
        final long id = RandomUtils.nextLong();
        pl.setId(id);

        assertThat(pl.getId()).isEqualTo(id);
    }
}