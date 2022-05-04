package eu.elision.marketplace.domain.users;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * This class contains the basic information of any user
 */
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
public abstract class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Firstname is mandatory")
    @Size(max = 15, min = 2, message = "Name can not be longer than 15 characters")
    private String firstName;
    @NotBlank(message = "Lastname is mandatory")
    @Size(max = 20, min = 2, message = "Name can not be longer than 15 characters")
    private String lastName;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format is wrong")
    private String email;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must have at least 8 characters, at least one uppercase and one lowercase letter and one number")
    private String password;
    private LocalDateTime createdDate;
    private boolean validated;

    protected User()
    {
        validated = false;
        createdDate = LocalDateTime.now();
    }
}
