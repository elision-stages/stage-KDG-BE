package eu.elision.marketplace.web.dtos.cart;

/**
 * Dto used for adding a product to a cart
 *
 * @param productId the id of the product you want to add
 * @param count     the quantity of products to add
 * @param add       when true, the quantity will be added to the quantity already in the cart. When false the pevious quantity will be overwritten
 */
public record AddProductToCartDto(long productId, int count, boolean add) {
}
