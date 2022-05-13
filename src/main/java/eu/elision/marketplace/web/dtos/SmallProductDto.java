package eu.elision.marketplace.web.dtos;

public record SmallProductDto(long id, String name, String category, String image, String description, double price) {
}
