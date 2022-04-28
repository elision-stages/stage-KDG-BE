package eu.elision.marketplace.domain.users;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class contains the info of an address
 */
@Getter
@Setter
@Entity
public class Address
{
    @Id
    @GeneratedValue
    private Long id;
    private String street;
    private String number;
    private String postalCode;
    private String city;
}
