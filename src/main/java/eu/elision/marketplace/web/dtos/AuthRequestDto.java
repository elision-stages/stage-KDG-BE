package eu.elision.marketplace.web.dtos;

import java.io.Serializable;

public record AuthRequestDto(String email, String password) implements Serializable {
}