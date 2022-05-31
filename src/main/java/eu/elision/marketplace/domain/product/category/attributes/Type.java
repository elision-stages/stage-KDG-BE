package eu.elision.marketplace.domain.product.category.attributes;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This enum is used to define the type of value of an attribute
 */
public enum Type {
    INTEGER,
    DECIMAL,
    STRING,
    BOOL;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
