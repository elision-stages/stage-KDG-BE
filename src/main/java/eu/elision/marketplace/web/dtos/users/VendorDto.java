package eu.elision.marketplace.web.dtos.users;

import java.io.Serializable;

/**
 * Dto object to transfer all data of a vendor
 *
 * @param firstName    the firstname of a vendor
 * @param lastName     the lastname of a vendor
 * @param email        the email of a vendor
 * @param password     the password of a vendor
 * @param validated    weather the vendor is validated
 * @param logo         the logo of a vendor
 * @param theme        the theme of a vendor
 * @param introduction the intro of a vendor
 * @param vatNumber    the vat number of a vendor
 * @param phoneNumber  the phone number of a vendor
 * @param businessName the business name of a vendor
 */
public record VendorDto(String firstName, String lastName, String email, String password, Boolean validated,
                        String logo, String theme,
                        String introduction, String vatNumber, String phoneNumber,
                        String businessName) implements Serializable
{
}
