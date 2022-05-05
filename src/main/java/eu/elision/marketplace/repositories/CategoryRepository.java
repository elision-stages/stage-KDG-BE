package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>
{
}