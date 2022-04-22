package eu.elision.marketplace.domain.product.category.attributes;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to dynamicly assign attributes to a category
 */
@Getter
@Setter
public class DynamicAttribute
{
    private String name;
    private boolean required;
    private Type type;
    private PickList enumList;
}
