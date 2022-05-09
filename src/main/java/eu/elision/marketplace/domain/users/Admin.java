package eu.elision.marketplace.domain.users;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;

/**
 * This class contains the extra info of an admin
 */
@Getter
@Setter
@Entity
public class Admin extends User {

}
