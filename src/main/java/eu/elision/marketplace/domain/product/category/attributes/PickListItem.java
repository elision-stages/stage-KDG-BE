package eu.elision.marketplace.domain.product.category.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class contains the value of an item of an enum attribute.
 */
@Getter
@Setter @AllArgsConstructor
public class PickListItem
{
    private String value;
}
