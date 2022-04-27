package eu.elision.marketplace.domain.product.category.attributes;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PickListItemTest {

    @Test
    void getSetValue() {
        PickListItem pli = new PickListItem();
        final String value = RandomStringUtils.random(4);

        pli.setValue(value);

        assertThat(pli.getValue()).hasToString(value);
    }

    @Test
    void allArgsConstructor(){
        final String value = RandomStringUtils.random(4);

        PickListItem pli = new PickListItem(value);
        assertThat(pli.getValue()).hasToString(value);
    }
}