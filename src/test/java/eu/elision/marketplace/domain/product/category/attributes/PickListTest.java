package eu.elision.marketplace.domain.product.category.attributes;

import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PickListTest {

    @Test
    void getSetCode() {
        PickList pickList = new PickList();
        final String code = HelperMethods.randomString(3);

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