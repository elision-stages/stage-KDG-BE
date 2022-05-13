package eu.elision.marketplace.domain.product.category;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to categorise orderLines
 */
@Getter
@Entity
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private Long id;
    @Setter
    private String name;
    @ManyToOne()
    @JoinColumn(name = "parent_id", referencedColumnName = "id", columnDefinition = "BIGINT")
    @Setter
    private Category parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Category> subCategories;
    @OneToMany
    private final List<DynamicAttribute> characteristics;

    public Category() {
        subCategories = new ArrayList<>();
        characteristics = new ArrayList<>();
    }
}
