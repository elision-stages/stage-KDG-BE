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
    private String name;
    private double price;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Vendor vendor;
    private String description;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
    @OneToMany
    private List<DynamicAttributeValue<?>> attributes;

    public Product()
    {
        this.images = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }
}
