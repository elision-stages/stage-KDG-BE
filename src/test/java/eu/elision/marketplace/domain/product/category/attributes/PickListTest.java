package eu.elision.marketplace.domain.product.category.attributes;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
}