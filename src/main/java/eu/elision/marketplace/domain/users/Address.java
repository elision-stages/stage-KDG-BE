package eu.elision.marketplace.domain.users;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains the info of an address
 */
@Getter
@Setter
public class Address
{
    private String street;
    private String number;
    private String postalCode;
    private String city;
}
