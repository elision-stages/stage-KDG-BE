package eu.elision.marketplace.domain.product.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is used to categorise orderLines
 */
@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;
    @Setter
    private String name;
    // columnDefinition BIGINT works for MySQL but H2 requires INT
    @JoinColumn(name = "parent_id", referencedColumnName = "id", columnDefinition = "BIGINT")
    @ManyToOne(cascade = CascadeType.MERGE)
    @Setter
    private Category parent;
    @JsonIgnore
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Category> subCategories;
    @OneToMany(mappedBy = "category")
    private final List<DynamicAttribute> characteristics;

    /**
     * No args constructor. Initialises the sub categories and characteristics array.
     */
    public Category() {
        subCategories = new HashSet<>();
        characteristics = new ArrayList<>();
    }
}
