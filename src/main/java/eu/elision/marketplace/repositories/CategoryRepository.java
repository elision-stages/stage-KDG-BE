package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Jpa repository for categories
 */
public interface CategoryRepository extends JpaRepository<Category, Long>
{
    /**
     * Find a category by name
     *
     * @param name the name of the category you want to find
     * @return the category with given name. Null if not found
     */
    Category findCategoryByName(String name);
}