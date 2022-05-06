package eu.elision.marketplace.services.helpers;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.web.dtos.CategoryDto;
import eu.elision.marketplace.web.dtos.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.DynamicAttributeDto;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    private Mapper() {
    }

    public static Category toCategory(CategoryMakeDto categoryMakeDto) {
        final Category category = new Category();
        category.setName(categoryMakeDto.name());
        return category;
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName(), new ArrayList<>());
    }

    public static List<CategoryDto> toCategoryDtoList(List<Category> categories) {
        List<CategoryDto> dtoList = new ArrayList<>();

        for (Category category : categories) {
            dtoList.add(new CategoryDto(category.getId(), category.getName(), category.getCharacteristics().stream().map(Mapper::toDynamicAttributeDto).toList()));
        }
        return dtoList;
    }

    public static DynamicAttributeDto toDynamicAttributeDto(DynamicAttribute dynamicAttribute) {
        return new DynamicAttributeDto(dynamicAttribute.getName(), dynamicAttribute.isRequired(), dynamicAttribute.getType(), dynamicAttribute.getEnumList().getItems().stream().map(PickListItem::getValue).toList());
    }
}

