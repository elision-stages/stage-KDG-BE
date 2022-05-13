package eu.elision.marketplace.services.helpers;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.web.dtos.CategoryDto;
import eu.elision.marketplace.web.dtos.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.DynamicAttributeDto;

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

    public static CartDto toCartDto(Cart cart) {
        CartDto cartDto = new CartDto(new ArrayList<>(), cart.getTotalPrice());
        for (OrderLine orderLine : cart.getOrderLines()) {
            final Product product = orderLine.getProduct();
            Collection<AttributeValue<String, String>> attributes = new ArrayList<>();

            for (DynamicAttributeValue<?> attribute : product.getAttributes()) {
                attributes.add(new AttributeValue<>(attribute.getAttributeName(), attribute.getValue().toString()));
            }

            cartDto.orderLines().add(new OrderLineDto(orderLine.getQuantity(), new ProductDto(product.getPrice(), product.getDescription(), product.getImages(), attributes, product.getVendor().getId())));
        }

        return cartDto;
    }
}

