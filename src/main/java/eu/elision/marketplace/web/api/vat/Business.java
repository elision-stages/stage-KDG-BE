package eu.elision.marketplace.web.api.vat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto used for a business vat check
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Business
{
    private String countryCode;
    private String vatNumber;
    private String name;
    private String address;
}
