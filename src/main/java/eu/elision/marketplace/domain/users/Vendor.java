package eu.elision.marketplace.domain.users;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains the extra info of a vendor
 */
@Getter
@Setter
public class Vendor extends User
{
    private String logo;
    private String theme;
    private String introduction;
    private String vatNumber;
}
