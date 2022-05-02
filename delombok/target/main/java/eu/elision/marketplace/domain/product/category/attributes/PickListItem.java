package eu.elision.marketplace.domain.product.category.attributes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class contains the value of an item of an enum attribute.
 */
@Entity
public class PickListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String value;

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getValue() {
        return this.value;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setValue(final String value) {
        this.value = value;
    }

    @SuppressWarnings("all")
    public PickListItem(final Long id, final String value) {
        this.id = id;
        this.value = value;
    }

    @SuppressWarnings("all")
    public PickListItem() {
    }
}
