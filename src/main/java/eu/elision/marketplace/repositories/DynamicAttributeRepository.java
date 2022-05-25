package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Jpa repository for dynamic attributes
 */
public interface DynamicAttributeRepository extends JpaRepository<DynamicAttribute, Long> {
    /**
     * Find a dynamic attribute by name
     *
     * @param name the name of the attribute you want to find
     * @return the attribute with given name
     */
    DynamicAttribute findDynamicAttributeByName(String name);

    /**
     * Check if an attribute with given name exists
     *
     * @param name the name you want to check
     * @return true if the attribute with name exists, false if it doesn't exist
     */
    boolean existsByName(String name);

    /**
     * Find all the attributes of a given category
     *
     * @param category the category whose attributes you need
     * @return a collection of attributes that belong to given category
     */
    Collection<DynamicAttribute> findAllByCategory(Category category);
}