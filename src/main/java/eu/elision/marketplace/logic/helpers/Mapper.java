package eu.elision.marketplace.logic.helpers;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Cart;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.UserDto;
import eu.elision.marketplace.web.dtos.attributes.AttributeValue;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.cart.CartDto;
import eu.elision.marketplace.web.dtos.cart.OrderLineDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.product.CategoryDto;
import eu.elision.marketplace.web.dtos.product.EditProductDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.dtos.product.SmallProductDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A helper class to map classes to other classes.
 */
public class Mapper {
    private Mapper() {
    }

    /**
     * Convert a CategoryMakeDto to a Category
     *
     * @param categoryMakeDto the categoryMakeDto that needs to be converted
     * @return a category object with the values of the given categoryMakeDto
     */
    public static Category toCategory(CategoryMakeDto categoryMakeDto) {
        final Category category = new Category();
        category.setName(categoryMakeDto.name());
        return category;
    }

    /**
     * Convert a category to a categoryDto
     *
     * @param category the category that needs to be converted
     * @return a categoryDto object with the values of the given category
     */
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName(), (category.getParent() == null ? null : category.getParent().getId()), category.getCharacteristics().stream().map(Mapper::toDynamicAttributeDto).toList());
    }

    /**
     * Convert a list of categories to a list of categoryDtos
     *
     * @param categories the list of categories that needs to be converted
     * @return a list of categoryDtos object with the values of the given list of categories
     */
    public static List<CategoryDto> toCategoryDtoList(List<Category> categories) {
        return categories.stream().map(Mapper::toCategoryDto).toList();
    }


    /**
     * Convert a Dynamic Attribute to a Dynamic Attribute Dto
     *
     * @param dynamicAttribute the Dynamic Attribute that needs to be converted
     * @return a Dynamic Attribute Dto object with the values of the given Dynamic Attribute
     */
    public static DynamicAttributeDto toDynamicAttributeDto(DynamicAttribute dynamicAttribute) {
        return new DynamicAttributeDto(dynamicAttribute.getName(), dynamicAttribute.isRequired(), dynamicAttribute.getType(), dynamicAttribute.getEnumList() != null ? dynamicAttribute.getEnumList().getItems().stream().map(PickListItem::getValue).toList() : null);
    }

    /**
     * Convert an Edit product dto object to a product object
     *
     * @param editProductDto  the data of the edit product dto
     * @param category        the category object of the product
     * @param vendor          the vendor object of the product
     * @param attributeValues the attribute values of the object
     * @return the product with all the data of the dto object
     */
    public static Product toProduct(EditProductDto editProductDto, Category category, Vendor vendor, List<DynamicAttributeValue<?>> attributeValues) {
        return new Product(editProductDto.id(), editProductDto.price(), editProductDto.title(), category, vendor, editProductDto.description(), editProductDto.images(), attributeValues);
    }


    /**
     * Convert a to User to a Dynamic User Dto
     *
     * @param user the User that needs to be converted
     * @return a User Dto object with the values of the given User
     */
    public static UserDto toUserDto(User user) {
        String role = "customer";
        if (user.getAuthorities().stream().anyMatch(predicate -> predicate.toString().equals("ROLE_ADMIN"))) {
            role = "admin";
        } else if (user.getAuthorities().stream().anyMatch(predicate -> predicate.toString().equals("ROLE_VENDOR"))) {
            role = "vendor";
        }
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), role);
    }

    /**
     * Convert a to Product to a Dynamic Small Product Dto
     *
     * @param product the Product that needs to be converted
     * @return a Small Product Dto object with the values of the given Product
     */
    public static SmallProductDto toSmallProductDto(Product product) {
        String image = product.getImages().isEmpty() ? null : product.getImages().get(0);
        return new SmallProductDto(product.getId(), product.getTitle(), product.getCategory().getName(), image, product.getDescription(), product.getPrice());
    }

    /**
     * Convert a to Cart to a Dynamic Small Cart Dto
     *
     * @param cart the Cart Dto that needs to be converted
     * @return a Cart Dto object with the values of the given Cart
     */
    public static CartDto toCartDto(Cart cart) {
        CartDto cartDto = new CartDto(new ArrayList<>(), cart.getTotalPrice());
        for (OrderLine orderLine : cart.getOrderLines()) {
            final Product product = orderLine.getProduct();
            Collection<AttributeValue<String, String>> attributes = new ArrayList<>();

            for (DynamicAttributeValue<?> attribute : product.getAttributes()) {
                attributes.add(new AttributeValue<>(attribute.getAttributeName(), attribute.getValue().toString()));
            }

            cartDto.orderLines().add(new OrderLineDto(orderLine.getQuantity(), new ProductDto(product.getPrice(), product.getDescription(), product.getTitle(), product.getImages(), product.getCategory(), attributes, product.getVendor().getId())));
        }

        return cartDto;
    }
}

