package eu.elision.marketplace.services;

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

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, DynamicAttributeService attributeService) {
        this.categoryRepository = categoryRepository;
        this.attributeService = attributeService;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(CategoryMakeDto categoryMakeDto) {
        Category category = categoryRepository.save(toCategory(categoryMakeDto));
        for (DynamicAttribute attr : attributeService.toDynamicAttributes(categoryMakeDto.characteristics())) {
            attributeService.save(attr);
        }
        return category;
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

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

    public Category findByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public Category findById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }


    public Collection<CategoryDto> findAllDto() {
        return categoryRepository.findAll().stream().map(this::toCategoryDto).toList();
    }

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

    private Category toCategory(CategoryMakeDto categoryMakeDto) {
        final Category category = new Category();
        category.setName(categoryMakeDto.name());

        return category;
    }

    public Category save(CategoryMakeDto categoryMakeDto, Collection<DynamicAttribute> dynamicAttributes) {
        Category category = categoryRepository.save(toCategory(categoryMakeDto));
        for (DynamicAttribute attr : dynamicAttributes) {
            attr.setCategory(category);
            attributeService.save(attr);
        }
        return category;
    }
}
