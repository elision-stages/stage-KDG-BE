package eu.elision.marketplace.web.dtos.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Attibute value dto
 *
 * @param <A> the name of the attribute
 * @param <V> the value of the attribute
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeValue<A, V> {
    private A attributeName;
    private V value;
}
