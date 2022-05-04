package eu.elision.marketplace.web.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair<A, V> {
    private A attributeName;
    private V attributeValue;
}
