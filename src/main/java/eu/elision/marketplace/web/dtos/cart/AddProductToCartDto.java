package eu.elision.marketplace.web.dtos.cart;

public record AddProductToCartDto(long productId, int count, boolean add)
{
}
