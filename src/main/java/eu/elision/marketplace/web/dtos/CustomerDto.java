package eu.elision.marketplace.web.dtos;

import java.io.Serializable;

public record CustomerDto(String name, String email, String password) implements Serializable {
}
