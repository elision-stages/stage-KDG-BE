package eu.elision.marketplace.domain.product.category;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to categorise orderLines
 */
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany
    private final List<Category> subCategories;
    @OneToMany
    private final List<DynamicAttribute> characteristics;

    public Category() {
        subCategories = new ArrayList<>();
        characteristics = new ArrayList<>();
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public List<Category> getSubCategories() {
        return this.subCategories;
    }

    @SuppressWarnings("all")
    public List<DynamicAttribute> getCharacteristics() {
        return this.characteristics;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }
}
