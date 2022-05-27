package eu.elision.marketplace.logic.services.product;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.repositories.CategoryRepository;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
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
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final DynamicAttributeService attributeService;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    /**
     * Category Service
     *
     * @param categoryRepository CategoryRepository (autowired)
     * @param attributeService   AttributeService (autowired)
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, DynamicAttributeService attributeService) {
        this.categoryRepository = categoryRepository;
        this.attributeService = attributeService;
    }

    /**
     * Get all the categories
     *
     * @return List with all the categories
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Save a category with a CategoryMakeDto
     *
     * @param categoryMakeDto DTO of category to save
     * @return Saved Category
     */
    public Category save(CategoryMakeDto categoryMakeDto) {
        return categoryRepository.save(toCategory(categoryMakeDto));
    }

    /**
     * Save a category
     *
     * @param category Category to save
     * @return Saved category
     */
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Save a category as a child of another category
     *
     * @param category Category to save
     * @param parentId ID of parent category
     */
    public void save(Category category, long parentId) {
        if (parentId != 0L) {
            Category parent =
                    categoryRepository.findById(parentId).orElse(null);
            if (parent == null) {
                throw new NotFoundException(String.format("Parent category with id %s not found", parentId));
            }
            category.setParent(parent);
        }
        categoryRepository.save(category);
    }

    /**
     * Get a category by its name
     *
     * @param name Name of category to look for
     * @return The found category
     */
    public Category findByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    /**
     * Get a category by its ID
     *
     * @param id ID of category to look for
     * @return The found category, null if the id is 0. If there is no category with given id, a not found exception will be thrown.
     */
    public Category findById(long id) {
        if (id == 0L) {
            logger.debug("Not looking for category with id 0");
            return null;
        }

        final Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            logger.error("Category with id {} not found", id);
            throw new NotFoundException(String.format("Category with id %s not found", id));
        }
        return category;
    }

    /**
     * Get all categories as DTOs
     *
     * @return A list with all the categories as DTO
     */
    public Collection<CategoryDto> findAllDto() {
        return categoryRepository.findAll().stream().map(this::toCategoryDto).toList();
    }

    /**
     * Convert a category to a category DTO
     *
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
     *
     * @param categoryMakeDto DTO of a category
     * @return A category
     */
    private Category toCategory(CategoryMakeDto categoryMakeDto) {
        final Category category = new Category();
        category.setName(categoryMakeDto.name());
        if (categoryMakeDto.parentId() > 0) category.setParent(categoryRepository.getById(categoryMakeDto.parentId()));

        return category;
    }

    /**
     * Save a category with attributes
     *
     * @param category          the category that needs to be saved
     * @param dynamicAttributes Collection of dynamic attributes
     * @return Saved category
     */
    public Category save(Category category, Collection<DynamicAttribute> dynamicAttributes)
    {
        category.setCharacteristics(dynamicAttributes.stream().toList());
        for (DynamicAttribute attr : dynamicAttributes)
        {
            attr.setCategory(category);
            attributeService.save(attr);
        }
        return category;
    }

    /**
     * Edit a category
     *
     * @param editCategoryDto   the global info of the category that needs to be edited
     * @param dynamicAttributes a collection of saved dynamic attributes
     */
    public void editCategory(CategoryDto editCategoryDto, Collection<DynamicAttribute> dynamicAttributes) {
        Category category = findById(editCategoryDto.id());

        category.setParent(findById(editCategoryDto.parentId()));
        category.setName(editCategoryDto.name());
        category.setCharacteristics(dynamicAttributes.stream().toList());

        categoryRepository.save(category);
    }
}
