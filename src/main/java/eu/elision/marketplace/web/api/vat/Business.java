package eu.elision.marketplace.web.api.vat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Business
{
    private String countryCode;
    private String vatNumber;
    private String name;
    private String address;
}
