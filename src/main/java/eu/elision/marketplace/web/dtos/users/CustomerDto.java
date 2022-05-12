package eu.elision.marketplace.web.dtos.users;

import java.io.Serializable;

public record CustomerDto(String firstName, String lastName, String email, String password) implements Serializable {
}
