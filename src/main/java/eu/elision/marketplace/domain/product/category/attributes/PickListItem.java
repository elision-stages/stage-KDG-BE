package eu.elision.marketplace.domain.product.category.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class contains the value of an item of an enum attribute.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PickListItem
{
    private String value;
}
