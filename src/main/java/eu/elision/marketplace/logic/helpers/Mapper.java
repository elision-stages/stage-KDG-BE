package eu.elision.marketplace.logic.helpers;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Cart;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.web.dtos.TokenDto;
import eu.elision.marketplace.web.dtos.UserDto;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.cart.CartDto;
import eu.elision.marketplace.web.dtos.cart.OrderLineDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import eu.elision.marketplace.web.dtos.order.OrderDto;
import eu.elision.marketplace.web.dtos.product.EditProductDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.dtos.product.SmallProductDto;
import eu.elision.marketplace.web.dtos.users.CustomerDto;
import eu.elision.marketplace.web.dtos.users.VendorDto;
import eu.elision.marketplace.web.dtos.users.VendorPageDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A helper class to map classes to other classes.
 */
// TODO: 9/06/22 use converters, populators 
public class Mapper
{
    private Mapper()
    {
    }

    /**
     * Convert a category to a categoryDto
     *
     * @param category the category that needs to be converted
     * @return a categoryDto object with the values of the given category
     */
    public static CategoryDto toCategoryDto(Category category)
    {
        return new CategoryDto(category.getId(), category.getName(), (category.getParent() == null ? null : category.getParent().getId()), category.getCharacteristics().stream().map(Mapper::toDynamicAttributeDto).toList());
    }

    /**
     * Convert a list of categories to a list of categoryDtos
     *
     * @param categories the list of categories that needs to be converted
     * @return a list of categoryDtos object with the values of the given list of categories
     */
    public static Collection<CategoryDto> toCategoryDto(Collection<Category> categories)
    {
        return categories.stream().map(Mapper::toCategoryDto).toList();
    }

    /**
     * Convert a Dynamic Attribute to a Dynamic Attribute Dto
     *
     * @param dynamicAttribute the Dynamic Attribute that needs to be converted
     * @return a Dynamic Attribute Dto object with the values of the given Dynamic Attribute
     */
    public static DynamicAttributeDto toDynamicAttributeDto(DynamicAttribute dynamicAttribute)
    {
        return new DynamicAttributeDto(dynamicAttribute.getName(), dynamicAttribute.isRequired(), dynamicAttribute.getType());
    }

    /**
     * Create a Product of all their separate attributes
     *
     * @param editProductDto  The dto with global info
     * @param category        the category in which the product needs to be added
     * @param vendor          the vendor that created the product
     * @param attributeValues the dynamic attributes that are part of the product
     * @return a Product
     */
    public static Product toProduct(EditProductDto editProductDto, Category category, Vendor vendor, List<DynamicAttributeValue<?>> attributeValues)
    {
        return new Product(editProductDto.id(), editProductDto.price(), editProductDto.title(), category, vendor, editProductDto.description(), editProductDto.images(), attributeValues);
    }


    /**
     * Convert a User to a Dynamic User Dto
     *
     * @param user the User that needs to be converted
     * @return a User Dto object with the values of the given User
     */
    public static UserDto toUserDto(User user)
    {
        String role = "customer";
        if (user.getAuthorities().stream().anyMatch(predicate -> predicate.toString().equals("ROLE_ADMIN")))
        {
            role = "admin";
        } else if (user.getAuthorities().stream().anyMatch(predicate -> predicate.toString().equals("ROLE_VENDOR")))
        {
            role = "vendor";
        }
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), role);
    }

    /**
     * Convert a customer dto object to a customer object
     *
     * @param customerDto the customer dto object that needs to be converted
     * @return the customer object with the data from the dto
     */
    public static Customer toCustomer(CustomerDto customerDto)
    {
        Customer customer = new Customer();

        customer.setFirstName(customerDto.firstName());
        customer.setLastName(customerDto.lastName());
        customer.setEmail(customerDto.email());
        customer.setPassword(customerDto.password());

        return customer;
    }

    /**
     * Convert a Product to a Dynamic Small Product Dto
     *
     * @param product the Product that needs to be converted
     * @return a Small Product Dto object with the values of the given Product
     */
    public static SmallProductDto toSmallProductDto(Product product)
    {
        return new SmallProductDto(
                product.getId(),
                product.getTitle(),
                product.getCategory() == null ? "Root" : product.getCategory().getName(),
                product.getCategory() == null ? 0L : product.getCategory().getId(),
                product.getImages().isEmpty() ? null : product.getImages().get(0),
                product.getDescription(),
                product.getPrice(),
                product.getVendor().getId(),
                product.getVendor().getBusinessName());
    }

    /**
     * Convert a list of products to small product dtos
     *
     * @param products the list of products that need to be converted
     * @return the collection of small product dtos
     */
    public static Collection<SmallProductDto> toSmallProductDto(Collection<Product> products)
    {
        return products.stream().map(Mapper::toSmallProductDto).toList();
    }

    /**
     * Map a product dto object to a product object
     *
     * @param productDto      the product dto with all the data of the product
     * @param attributeValues the saved attribute values
     * @param vendor          the vendor of the product
     * @param category        the category of the product
     * @return the product object with all the given data
     */
    public static Product toProduct(ProductDto productDto, Collection<DynamicAttributeValue<?>> attributeValues, Vendor vendor, Category category)
    {
        Product product = new Product();
        product.setPrice(productDto.price());
        product.setTitle(productDto.title());
        product.setVendor(vendor);
        product.setDescription(productDto.description());
        product.setImages(productDto.images());
        product.setCategory(category);
        product.setAttributes(attributeValues.stream().toList());

        return product;
    }

    /**
     * Convert a Cart to a Dynamic Small Cart Dto
     *
     * @param cart the Cart Dto that needs to be converted
     * @return a Cart Dto object with the values of the given Cart
     */
    public static CartDto toCartDto(Cart cart)
    {
        CartDto cartDto = new CartDto(new ArrayList<>(), cart.getTotalPrice());
        cart.getOrderLines().forEach(orderLine -> cartDto.orderLines().add(toOrderLineDto(orderLine)));

        return cartDto;
    }

    /**
     * Convert a list of orders into a list of order dto
     *
     * @param orders the list of orders that needs to be converted
     * @return The list of order dto
     */
    public static Collection<OrderDto> toOrderDto(Collection<Order> orders)
    {
        return orders.stream().map(Mapper::toOrderDto).toList();
    }

    /**
     * Convert an order to a order dto
     *
     * @param order the order that needs to be converted
     * @return the order dto
     */
    public static OrderDto toOrderDto(Order order)
    {
        return new OrderDto(order.getOrderNumber(), order.getUser().getFullName(), order.getCreatedDate().toString(), order.getTotalPrice(), order.getProductCount());
    }

    /**
     * Takes an OrderLine and converts it to a data transfer object
     *
     * @param orderLine input Order line
     * @return output DTO
     */
    public static OrderLineDto toOrderLineDto(OrderLine orderLine)
    {
        return new OrderLineDto(orderLine.getQuantity(), toSmallProductDto(orderLine.getProduct()));
    }

    /**
     * Create a vendor dto object for vendor page
     *
     * @param vendorById         the vendor with the information about the vendor
     * @param productsByVendorId the products of given vendor
     * @return the dto object with the data and products from given vendor
     */
    public static VendorPageDto toVendorPageDto(Vendor vendorById, Collection<Product> productsByVendorId)
    {
        return new VendorPageDto(vendorById.getEmail(), vendorById.getBusinessName(), vendorById.getLogo(), vendorById.getPhoneNumber(), vendorById.getIntroduction(), vendorById.getVatNumber(), vendorById.getTheme(), Mapper.toSmallProductDto(productsByVendorId));
    }

    /**
     * Map a vendor dto object to a vendor object
     *
     * @param vendorDto the dto object with the vendor data
     * @return the vendor with the original data
     */
    public static Vendor toVendor(VendorDto vendorDto)
    {
        Vendor vendor = new Vendor();
        vendor.setIntroduction(vendorDto.introduction());
        vendor.setLogo(vendorDto.logo());
        vendor.setTheme(vendorDto.theme());
        vendor.setEmail(vendorDto.email());
        vendor.setFirstName(vendorDto.firstName());
        vendor.setLastName(vendorDto.lastName());
        vendor.setPassword(vendorDto.password());
        vendor.setValidated(vendorDto.validated());
        vendor.setVatNumber(vendorDto.vatNumber());
        vendor.setPhoneNumber(vendorDto.phoneNumber());
        vendor.setBusinessName(vendorDto.businessName());
        return vendor;
    }

    /**
     * Convert a string to a toke dto
     *
     * @param token the string that needs to be converted
     * @return the token dto with given token
     */
    public static TokenDto toTokenDto(String token)
    {
        return new TokenDto(token);
    }


    /**
     * Convert a DynamicAttributeDto to a DynamicAttribute
     *
     * @param dynamicAttributeDto DynamicAttributeDto to convert to a DynamicAttribute
     * @param category            the category that needs to be set to the attribute
     * @return The DynamicAttribute
     */
    public static DynamicAttribute toDynamicAttribute(DynamicAttributeDto dynamicAttributeDto, Category category)
    {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        dynamicAttribute.setName(dynamicAttributeDto.getName());
        dynamicAttribute.setRequired(dynamicAttributeDto.isRequired());
        dynamicAttribute.setType(dynamicAttributeDto.getType());
        dynamicAttribute.setCategory(category);

        return dynamicAttribute;
    }

    /**
     * Convert a collection of DynamicAttributeDto to DynamicAttribute
     *
     * @param dynamicAttributeDtos Collection of DTOs
     * @param category             the category that needs to be set to the attributes
     * @return Collection of DynamicAttribute
     */
    public static Collection<DynamicAttribute> toDynamicAttributes(Collection<DynamicAttributeDto> dynamicAttributeDtos, Category category)
    {
        return dynamicAttributeDtos.stream().map(dynamicAttributeDto -> Mapper.toDynamicAttribute(dynamicAttributeDto, category)).toList();
    }
}

