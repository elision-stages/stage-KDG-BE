package eu.elision.marketplace.web.dtos.product;

/**
 * A dto to transfer minimal data about a product
 *
 * @param id          the id of the product
 * @param name        the title of the product
 * @param category    the name of the category of the product
 * @param image       the url of the main image of the product
 * @param description the description of the product
 * @param price       the price of the product
 */
public record SmallProductDto(long id, String name, String category, long categoryId, String image, String description,
                              double price, long vendorId, String vendorName)
{
}

