package eu.elision.marketplace.domain.product.category.attributes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PickListItemTest {

    @Test
    void getSetValue() {
        PickListItem pli = new PickListItem();

        pli.setValue("test");

        assertThat(pli.getValue()).hasToString("test");
    }

    @Test
    void allArgsConstructor(){
        PickListItem pli = new PickListItem("test");
        assertThat(pli.getValue()).hasToString("test");
    }
}