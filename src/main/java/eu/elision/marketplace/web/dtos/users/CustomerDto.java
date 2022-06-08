package eu.elision.marketplace.web.dtos.users;

import java.io.Serializable;

/**
 * Dto to make a new customer
 *
 * @param firstName the first name of the customer
 * @param lastName  the last name of the customer
 * @param email     the email of the customer
 * @param password  the password of the customer
 */
public record CustomerDto(String firstName, String lastName, String email, String password) implements Serializable {
}
