package eu.elision.marketplace.web.dtos.users;

import java.util.Collection;

/**
 * Dto used to transfer the data for the vendor page
 *
 * @param email        the email of a vendor
 * @param businessName the business name of a vendor
 * @param logo         the logo of a vendor
 * @param phoneNumber  the phone number of a vendor
 * @param introduction the introduction of a vendor
 * @param vatNumber    the vatnumber of a vendor
 * @param theme        the theme of a vendor
 * @param products     the products of a vendor
 */
public record VendorPageDto(String email, String businessName, String logo, String phoneNumber, String introduction,
                            String vatNumber,
                            String theme, Collection<eu.elision.marketplace.web.dtos.product.SmallProductDto> products)
{
}
