package eu.elision.marketplace.domain.product.category.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class contains the value of an item of an enum attribute.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PickListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String value;

    /**
     * Constructor with value
     *
     * @param value the value of the pick list item
     */
    public PickListItem(String value) {
        this.value = value;
    }
}
