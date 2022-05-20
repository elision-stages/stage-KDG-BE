package eu.elision.marketplace.web.api.vat;

import lombok.Getter;
import lombok.Setter;

/**
 * Dto used for a business vat check
 */
@Getter
@Setter
public class Business
{
    private String countryCode;
    private String vatNumber;
    private String name;
    private String address;
}
