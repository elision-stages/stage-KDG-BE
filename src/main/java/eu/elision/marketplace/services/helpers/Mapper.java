package eu.elision.marketplace.services.helpers;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.product.CategoryDto;
import eu.elision.marketplace.web.dtos.product.EditProductDto;

import java.util.List;

public class Mapper
{
    private Mapper()
    {
    }

    public static Category toCategory(CategoryMakeDto categoryMakeDto)
    {
        final Category category = new Category();
        category.setName(categoryMakeDto.name());
        return category;
    }

    public static CategoryDto toCategoryDto(Category category)
    {
        return new CategoryDto(category.getId(), category.getName(), category.getCharacteristics().stream().map(Mapper::toDynamicAttributeDto).toList());
    }

    public static List<CategoryDto> toCategoryDtoList(List<Category> categories)
    {
        return categories.stream().map(Mapper::toCategoryDto).toList();
    }

    public static DynamicAttributeDto toDynamicAttributeDto(DynamicAttribute dynamicAttribute)
    {
        return new DynamicAttributeDto(dynamicAttribute.getName(), dynamicAttribute.isRequired(), dynamicAttribute.getType(), dynamicAttribute.getEnumList() != null ? dynamicAttribute.getEnumList().getItems().stream().map(PickListItem::getValue).toList() : null);
    }

    public static Product toProduct(EditProductDto editProductDto, Vendor vendor, List<DynamicAttributeValue<?>> attributeValues)
    {
        return new Product(editProductDto.id(), editProductDto.price(), editProductDto.title(), vendor, editProductDto.description(), editProductDto.images(), attributeValues);
    }

}

