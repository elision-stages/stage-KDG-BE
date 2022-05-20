package eu.elision.marketplace.web.dtos;

import java.io.Serializable;

/**
 * Dto with an email and password
 */
public record AuthRequestDto(String email, String password) implements Serializable {
}