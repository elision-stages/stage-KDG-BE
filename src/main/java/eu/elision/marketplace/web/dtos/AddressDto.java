package eu.elision.marketplace.web.dtos;

import java.io.Serializable;

public record AddressDto(String street, String number, String postalCode, String city) implements Serializable
{
}
