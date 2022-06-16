package eu.elision.marketplace.web.dtos;

/**
 * Dto to pass all user iformation
 *
 * @param id        the id of the user
 * @param firstName the first name of the user
 * @param lastName  the last name of the user
 * @param email     the email of the user
 * @param role      the role of the user
 */
public record UserDto(Long id, String firstName, String lastName, String email, String role) {
}