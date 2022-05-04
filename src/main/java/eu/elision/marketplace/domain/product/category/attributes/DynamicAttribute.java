package eu.elision.marketplace.domain.product.category.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * This class is used to dynamicly assign attributes to a category
 */
@Getter
@Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class DynamicAttribute
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean required;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne
    private PickList enumList;
}
