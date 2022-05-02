package eu.elision.marketplace.domain.product.category.attributes;

import javax.persistence.*;
import java.util.List;

/**
 * This class is used when the attribute of a category is an enum. It contains a list of items that can be chosen.
 */
@Entity
public class PickList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    @OneToMany
    private List<PickListItem> items;

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getCode() {
        return this.code;
    }

    @SuppressWarnings("all")
    public List<PickListItem> getItems() {
        return this.items;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setCode(final String code) {
        this.code = code;
    }

    @SuppressWarnings("all")
    public void setItems(final List<PickListItem> items) {
        this.items = items;
    }
}
