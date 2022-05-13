package eu.elision.marketplace.services.helpers;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.web.dtos.*;

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
        return new CategoryDto(category.getId(), category.getName(), (category.getParent() == null ? null : category.getParent().getId()), category.getCharacteristics().stream().map(Mapper::toDynamicAttributeDto).toList());
    }

    public static List<CategoryDto> toCategoryDtoList(List<Category> categories) {
        return categories.stream().map(Mapper::toCategoryDto).toList();
    }

    public static DynamicAttributeDto toDynamicAttributeDto(DynamicAttribute dynamicAttribute) {
        return new DynamicAttributeDto(dynamicAttribute.getName(), dynamicAttribute.isRequired(), dynamicAttribute.getType(), dynamicAttribute.getEnumList() != null ? dynamicAttribute.getEnumList().getItems().stream().map(PickListItem::getValue).toList() : null);
    }

    public static UserDto toUserDto(User user) {
        String role = "customer";
        if(user.getAuthorities().stream().anyMatch(predicate -> predicate.toString().equals("ROLE_ADMIN"))) {
            role = "admin";
        }else if(user.getAuthorities().stream().anyMatch(predicate -> predicate.toString().equals("ROLE_VENDOR"))) {
            role = "vendor";
        }
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), role);
    }

    public static SmallProductDto toSmallProductDto(Product product) {
        String image = product.getImages().isEmpty() ? null : product.getImages().get(0);
        return new SmallProductDto(product.getId(), product.getName(), product.getCategory().getName(), image, product.getDescription(), product.getPrice());
    }
}

