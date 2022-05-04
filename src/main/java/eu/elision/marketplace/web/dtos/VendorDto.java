package eu.elision.marketplace.web.dtos;

import java.io.Serializable;

public record VendorDto(String firstName, String lastName, String email, String password, String logo, String theme,
                        String introduction, String vatNumber, String phoneNumber,
                        String businessName) implements Serializable
{
}
