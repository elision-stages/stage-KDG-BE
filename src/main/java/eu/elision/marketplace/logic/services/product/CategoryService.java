package eu.elision.marketplace.logic.services.product;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.repositories.CategoryRepository;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.product.CategoryDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
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
    private final DynamicAttributeService attributeService;

    /**
     * Category Service
     * @param categoryRepository CategoryRepository (autowired)
     * @param attributeService AttributeService (autowired)
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, DynamicAttributeService attributeService)
    {
        this.categoryRepository = categoryRepository;
        this.attributeService = attributeService;
    }

    /**
     * Get all the categories
     * @return List with all the categories
     */
    public List<Category> findAll()
    {
        return categoryRepository.findAll();
    }

    /**
     * Save a category with a CategoryMakeDto
     * @param categoryMakeDto DTO of category to save
     * @return Saved Category
     */
    public Category save(CategoryMakeDto categoryMakeDto) {
        return categoryRepository.save(toCategory(categoryMakeDto));
    }

    /**
     * Save a category
     * @param category Category to save
     * @return Saved category
     */
    public Category save(Category category)
    {
        return categoryRepository.save(category);
    }

    /**
     * Save a category as a child of another category
     * @param category Category to save
     * @param parentId ID of parent category
     */
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
        }
        categoryRepository.save(category);
    }

    /**
     * Get a category by its name
     * @param name Name of category to look for
     * @return The found category
     */
    public Category findByName(String name)
    {
        return categoryRepository.findCategoryByName(name);
    }

    /**
     * Get a category by its ID
     * @param id ID of category to look for
     * @return The found category
     */
    public Category findById(long id)
    {
        return categoryRepository.findById(id).orElse(null);
    }

    /**
     * Get all categories as DTOs
     * @return A list with all the categories as DTO
     */
    public Collection<CategoryDto> findAllDto() {
        return categoryRepository.findAll().stream().map(this::toCategoryDto).toList();
    }

    /**
     * Convert a category to a category DTO
     * @param category A category
     * @return The category DTO
     */
    public CategoryDto toCategoryDto(Category category) {
        Collection<DynamicAttributeDto> characteristics = new ArrayList<>();

        for (DynamicAttribute characteristic : category.getCharacteristics()) {
            characteristics.add(
                    new DynamicAttributeDto(characteristic.getName(),
                            characteristic.isRequired(),
                            characteristic.getType(),
                            (characteristic.getEnumList() != null ? characteristic.getEnumList().getItems().stream().map(PickListItem::getValue).toList() : null)));
        }

        return new CategoryDto(
                category.getId(),
                category.getName(),
                (category.getParent() == null ? null : category.getParent().getId()),
                characteristics
        );
    }

    /**
     * Convert a categoryDTO to a category
     * @param categoryMakeDto DTO of a category
     * @return A category
     */
    private Category toCategory(CategoryMakeDto categoryMakeDto)
    {
        final Category category = new Category();
        category.setName(categoryMakeDto.name());
        if(categoryMakeDto.parentId() > 0) category.setParent(categoryRepository.getById(categoryMakeDto.parentId()));

        return category;
    }

    /**
     * Save a category with attributes
     * @param categoryMakeDto DTO of the category
     * @param dynamicAttributes Collection of dynamic attributes
     * @return Saved category
     */
    public Category save(CategoryMakeDto categoryMakeDto, Collection<DynamicAttribute> dynamicAttributes) {
        Category category = categoryRepository.save(toCategory(categoryMakeDto));
        for (DynamicAttribute attr : dynamicAttributes)
        {
            attr.setCategory(category);
            attributeService.save(attr);
        }
        return category;
    }
}
