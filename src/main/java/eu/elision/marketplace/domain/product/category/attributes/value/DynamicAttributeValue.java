package eu.elision.marketplace.domain.product.category.attributes.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * This class contains the name and value of the attribute of the category
 *
 * @param <T> The value of the attribute
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DynamicAttributeValue<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String attributeName;

    protected DynamicAttributeValue(String attributeName)
    {
        this.attributeName = attributeName;
    }

    /**
     * Get the value of the attribute
     *
     * @return the value of the attribute
     */
    public abstract T getValue();
}
