package eu.elision.marketplace.domain.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * This class contains the basic information of any user
 */
@Getter @Setter
public abstract class User
{
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdDate;
    private boolean validated;
}
