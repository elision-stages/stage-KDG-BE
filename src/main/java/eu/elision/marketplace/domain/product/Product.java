package eu.elision.marketplace.domain.product;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This product contains the info of a product
 */
@Data
@AllArgsConstructor
@Entity
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private double price;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Category category;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Vendor vendor;
    private String description;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> images;
    @OneToMany(fetch = FetchType.EAGER)
    private List<DynamicAttributeValue<?>> attributes;

    /**
     * Constructor of product. Creates a new product with empty parameters
     */
    public Product()
    {
        this.images = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }
}
