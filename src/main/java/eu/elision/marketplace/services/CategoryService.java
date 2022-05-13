package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.repositories.CategoryRepository;
import eu.elision.marketplace.web.dtos.CategoryDto;
import eu.elision.marketplace.web.dtos.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.DynamicAttributeDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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
                characteristics
        );
    }

    private Category toCategory(CategoryMakeDto categoryMakeDto, Collection<DynamicAttribute> dynamicAttributes) {
        final Category category = new Category();
        category.setName(categoryMakeDto.name());
        category.getCharacteristics().addAll(dynamicAttributes);

        return category;
    }

    public void save(CategoryMakeDto categoryMakeDto, Collection<DynamicAttribute> dynamicAttributes) {
        categoryRepository.save(toCategory(categoryMakeDto, dynamicAttributes));
    }
}
