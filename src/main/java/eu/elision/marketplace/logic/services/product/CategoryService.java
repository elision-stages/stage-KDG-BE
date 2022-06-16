package eu.elision.marketplace.logic.services.product;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.repositories.CategoryRepository;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service for categories
 */
@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    /**
     * Category Service
     *
     * @param categoryRepository CategoryRepository (autowired)
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    //--------------------------------------------- save / edit categories

    /**
     * Save a category with a CategoryMakeDto
     *
     * @param categoryMakeDto DTO of category to save
     * @return Saved Category
     */
    public Category save(CategoryMakeDto categoryMakeDto)
    {
        return categoryRepository.save(toCategory(categoryMakeDto));
    }

    /**
     * Save a category
     *
     * @param category Category to save
     * @return Saved category
     */
    public Category save(Category category)
    {
        category.getCharacteristics().forEach(dynamicAttribute -> dynamicAttribute.setCategory(category));
        return categoryRepository.save(category);
    }

    /**
     * Edit a category
     *
     * @param editCategoryDto   the global info of the category that needs to be edited
     * @param dynamicAttributes a collection of saved dynamic attributes
     * @return The edited category
     */
    public Category editCategory(CategoryDto editCategoryDto, Collection<DynamicAttribute> dynamicAttributes)
    {
        Category category = findById(editCategoryDto.getId());

        category.setParent(findById(editCategoryDto.getParentId()));
        category.setName(editCategoryDto.getName());
        category.setCharacteristics(new ArrayList<>(dynamicAttributes.stream().toList()));

        return categoryRepository.save(category);
    }

    //--------------------------------------------- find categories

    /**
     * Get all the categories
     *
     * @return List with all the categories
     */
    public List<Category> findAll()
    {
        return categoryRepository.findAll();
    }

    /**
     * Get a category by its name
     *
     * @param name Name of category to look for
     * @return The found category
     */
    public Category findByName(String name)
    {
        return categoryRepository.findCategoryByName(name);
    }

    /**
     * Get a category by its ID
     *
     * @param id ID of category to look for
     * @return The found category, null if the id is 0. If there is no category with given id, a not found exception will be thrown.
     */
    public Category findById(long id)
    {
        // When categories don't have a parent, the web controller will pass on 0L as value. Without this check, an exception woul be thrown
        if (id == 0L)
        {
            logger.debug("Not looking for category with id 0");
            return null;
        }

        final Category category = categoryRepository.findById(id).orElse(null);
        if (category == null)
        {
            throw new NotFoundException(String.format("Category with id %s not found", id));
        }
        return category;
    }

    //-------------------------------------------------------- converters

    /**
     * Convert a categoryDTO to a category
     *
     * @param categoryMakeDto DTO of a category
     * @return A category
     */
    private Category toCategory(CategoryMakeDto categoryMakeDto)
    {
        final Category category = new Category();
        category.setName(categoryMakeDto.name());

        if (categoryMakeDto.parentId() > 0)
            category.setParent(findById(categoryMakeDto.parentId()));

        return category;
    }
}
