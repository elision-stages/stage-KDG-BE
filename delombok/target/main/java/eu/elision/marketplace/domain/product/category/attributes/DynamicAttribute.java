package eu.elision.marketplace.domain.product.category.attributes;

import javax.persistence.*;

/**
 * This class is used to dynamicly assign attributes to a category
 */
@Entity
public class DynamicAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean required;
    @ManyToOne
    private Type type;
    @ManyToOne
    private PickList enumList;

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public boolean isRequired() {
        return this.required;
    }

    @SuppressWarnings("all")
    public Type getType() {
        return this.type;
    }

    @SuppressWarnings("all")
    public PickList getEnumList() {
        return this.enumList;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setRequired(final boolean required) {
        this.required = required;
    }

    @SuppressWarnings("all")
    public void setType(final Type type) {
        this.type = type;
    }

    @SuppressWarnings("all")
    public void setEnumList(final PickList enumList) {
        this.enumList = enumList;
    }

    @SuppressWarnings("all")
    public DynamicAttribute(final Long id, final String name, final boolean required, final Type type, final PickList enumList) {
        this.id = id;
        this.name = name;
        this.required = required;
        this.type = type;
        this.enumList = enumList;
    }

    @SuppressWarnings("all")
    public DynamicAttribute() {
    }
}
