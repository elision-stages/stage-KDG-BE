package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Jpa repository for categories
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);
}