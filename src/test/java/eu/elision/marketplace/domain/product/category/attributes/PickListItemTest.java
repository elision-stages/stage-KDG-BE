package eu.elision.marketplace.domain.product.category.attributes;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
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
    void allArgsConstructor() {
        final String value = RandomStringUtils.random(4);

        PickListItem pli = new PickListItem(RandomUtils.nextLong(1, 10), value);
        assertThat(pli.getValue()).hasToString(value);
    }

    @Test
    void getSetId() {
        PickListItem pli = new PickListItem();
        final long id = RandomUtils.nextLong();
        pli.setId(id);

        assertThat(pli.getId()).isEqualTo(id);
    }
}