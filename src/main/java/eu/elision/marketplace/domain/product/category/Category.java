package eu.elision.marketplace.domain.product.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to categorise orderLines
 */
@Getter
@Entity
public class Category implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter
    private Long id;
    @Setter
    @Column(length = 100)
    @Size(max = 100, min = 2, message = "Category name must contain between 2 and 50.000 characters")
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
    @Setter
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<DynamicAttribute> characteristics;

    /**
     * No args constructor. Initialises the sub categories and characteristics array.
     */
    public Category() {
        subCategories = new HashSet<>();
        characteristics = new ArrayList<>();
    }
}
