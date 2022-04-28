package eu.elision.marketplace.web.dtos;

import java.io.Serializable;

public record AddressDto(Long id, String street, String number, String postalCode, String city) implements Serializable
{
}
