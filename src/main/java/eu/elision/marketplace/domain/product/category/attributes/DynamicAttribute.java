package eu.elision.marketplace.domain.product.category.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.elision.marketplace.domain.product.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * This class is used to dynamicly assign attributes to a category
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DynamicAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(length = 250)
    @Size(max = 250, min = 2, message = "Dynamic attribute name must contain between 2 and 50.000 characters")
    private String name;
    private boolean required;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne(cascade = CascadeType.ALL)
    private PickList enumList;
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id", columnDefinition = "BIGINT")
    private Category category;

}
