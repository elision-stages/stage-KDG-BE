package eu.elision.marketplace.domain.users;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @NotBlank(message = "VAT number is required")
    @Length(min = 9, message = "VAT number is invalid")
    private String vatNumber;
    @Length(min = 9, message = "Phone number format is not right")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d*$", message = "Phone number can only include numbers")
    private String phoneNumber;
    @NotBlank(message = "Business name is required")
    private String businessName;
}
