package eu.elision.marketplace.web.dtos;

public record UserDto(Long id, String firstName, String lastName, String email, String role) {
}