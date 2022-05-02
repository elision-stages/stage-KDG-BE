package eu.elision.marketplace.web.dtos;

import java.io.Serializable;

public record VendorDto(String name, String email, String password, boolean validated, String logo, String theme,
                        String introduction, String vatNumber) implements Serializable
{
}
