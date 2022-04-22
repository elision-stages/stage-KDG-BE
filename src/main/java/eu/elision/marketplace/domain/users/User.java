package eu.elision.marketplace.domain.users;

import java.time.LocalDateTime;

public class User
{
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdDate;
    private boolean validated;
}
