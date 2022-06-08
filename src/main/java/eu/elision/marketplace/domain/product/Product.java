package eu.elision.marketplace.domain.product;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This product contains the info of a product
 */
@Getter
@Setter
@AllArgsConstructor
@Entity
public class Product implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    @Column(length = 250)
    @Size(max = 250, min = 2, message = "Product title must contain between 2 and 250 characters")
    private String title;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Category category;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Vendor vendor;
    @Column(length = 50000, columnDefinition = "text")
    @Size(max = 50000, min = 2, message = "Product description must contain between 2 and 50.000 characters")
    private String description;
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<String> images;
    @OneToMany(fetch = FetchType.EAGER)
    private List<DynamicAttributeValue<?>> attributes;

    /**
     * Constructor of product. Creates a new product with empty parameters
     */
    public Product() {
        this.images = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }

    /**
     * Remove the attributes that are not in the category of the product
     */
    public void removeNonCategoryAttributes() {
        attributes.removeIf(attribute -> category.getCharacteristics().stream().noneMatch(characteristic -> Objects.equals(attribute.getAttributeName(), characteristic.getName())));
    }
}
