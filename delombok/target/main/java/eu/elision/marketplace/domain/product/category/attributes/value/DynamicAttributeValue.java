package eu.elision.marketplace.domain.product.category.attributes.value;

import javax.persistence.*;

/**
 * This class contains the name and value of the attribute of the category
 *
 * @param <T> The value of the attribute
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DynamicAttributeValue<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String attributeName;
    private T value;

    /**
     * Get the value of the attribute
     *
     * @return the value of the attribute
     */
    public T getValue() {
        return value;
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getAttributeName() {
        return this.attributeName;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setAttributeName(final String attributeName) {
        this.attributeName = attributeName;
    }

    @SuppressWarnings("all")
    public void setValue(final T value) {
        this.value = value;
    }

    @SuppressWarnings("all")
    public DynamicAttributeValue(final Long id, final String attributeName, final T value) {
        this.id = id;
        this.attributeName = attributeName;
        this.value = value;
    }

    @SuppressWarnings("all")
    public DynamicAttributeValue() {
    }
}
