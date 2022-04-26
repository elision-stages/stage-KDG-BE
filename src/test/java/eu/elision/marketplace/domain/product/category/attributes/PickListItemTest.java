package eu.elision.marketplace.domain.product.category.attributes;

import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PickListItemTest {

    @Test
    void getSetValue() {
        PickListItem pli = new PickListItem();
        final String value = HelperMethods.randomString(10);

        pli.setValue(value);

        assertThat(pli.getValue()).hasToString(value);
    }

    @Test
    void allArgsConstructor(){
        final String value = HelperMethods.randomString(10);

        PickListItem pli = new PickListItem(value);
        assertThat(pli.getValue()).hasToString(value);
    }
}