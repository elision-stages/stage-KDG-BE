package eu.elision.marketplace.domain.product.category.attributes;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * This class is used when the attribute of a category is an enum. It contains a list of items that can be chosen.
 */
@Getter
@Setter
@Entity
public class PickList
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PickListItem> items;
}
