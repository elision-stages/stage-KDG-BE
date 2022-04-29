package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.repositories.CategoryRepository;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void save(Category category, long parentId) {
        if (parentId != 0L) {
            Category parent =
                    categoryRepository.findById(parentId).orElse(null);
            if (parent == null) {
                throw new NotFoundException(String.format("Parent category with id %s not found", parentId));
            }
            parent.getSubCategories().add(category);
            categoryRepository.save(category);
            categoryRepository.save(parent);
        } else
            categoryRepository.save(category);
    }
}
