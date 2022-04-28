package eu.elision.marketplace.domain.users;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * This class contains the extra info of a vendor
 */
@Getter
@Setter
@Entity
public class Vendor extends User
{
    private String logo;
    private String theme;
    private String introduction;
    private String vatNumber;
}
