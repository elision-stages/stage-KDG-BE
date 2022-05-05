package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.repositories.CategoryRepository;
import eu.elision.marketplace.web.dtos.CategoryDto;
import eu.elision.marketplace.web.dtos.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.DynamicAttributeDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public Collection<CategoryDto> findAllDto()
    {
        return categoryRepository.findAll().stream().map(this::toCategoryDto).toList();
    }

    public CategoryDto toCategoryDto(Category category)
    {
        Collection<DynamicAttributeDto> characteristics = new ArrayList<>();

        for (DynamicAttribute characteristic : category.getCharacteristics())
        {
            characteristics.add(new DynamicAttributeDto(characteristic.getName(), characteristic.isRequired(), characteristic.getType(),
                    characteristic.getEnumList().getItems().stream().map(PickListItem::getValue).toList()));
        }

        return new CategoryDto(
                category.getId(),
                category.getName(),
                characteristics
        );
    }

    private Category toCategory(CategoryMakeDto categoryMakeDto, Collection<DynamicAttribute> dynamicAttributes)
    {
        final Category category = new Category();
        category.setName(categoryMakeDto.name());
        category.getCharacteristics().addAll(dynamicAttributes);

        return category;
    }

    public void save(CategoryMakeDto categoryMakeDto, Collection<DynamicAttribute> dynamicAttributes)
    {
        categoryRepository.save(toCategory(categoryMakeDto, dynamicAttributes));
    }
}
