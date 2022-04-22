package eu.elision.marketplace.domain.product.category.attributes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used when the attribute of a category is an enum. It contains a list of items that can be chosen.
 */
@Getter
@Setter
public class PickList
{
    private String code;
    private List<PickListItem> items;
}
