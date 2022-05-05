package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.repositories.CategoryRepository;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll()
    {
        return categoryRepository.findAll();
    }

    public Category save(Category category)
    {
        return categoryRepository.save(category);
    }

    @Transactional
    public void save(Category category, long parentId)
    {
        if (parentId != 0L)
        {
            Category parent =
                    categoryRepository.findById(parentId).orElse(null);
            if (parent == null)
            {
                throw new NotFoundException(String.format("Parent category with id %s not found", parentId));
            }
            category.setParent(parent);
            parent.getSubCategories().add(category);
            categoryRepository.save(category);
            categoryRepository.save(parent);
        } else
            categoryRepository.save(category);
    }

    public Category findByName(String name)
    {
        return categoryRepository.findCategoryByName(name);
    }

    public Category findById(long id)
    {
        return categoryRepository.findById(id).orElse(null);
    }
}
