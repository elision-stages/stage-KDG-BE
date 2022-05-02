package eu.elision.marketplace.domain.users;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * This class contains the basic information of any user
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(max = 15, min = 2, message = "Name can not be longer than 15 characters")
    private String name;
    @NotBlank(message = "Name is mandatory")
    @Email(message = "Email format is wrong")
    private String email;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must have at lest 8 characters, at least one uppercase and one lowercase letter and one number")
    private String password;
    private LocalDateTime createdDate;
    private boolean validated;

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    @SuppressWarnings("all")
    public String getPassword() {
        return this.password;
    }

    @SuppressWarnings("all")
    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    @SuppressWarnings("all")
    public boolean isValidated() {
        return this.validated;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    @SuppressWarnings("all")
    public void setPassword(final String password) {
        this.password = password;
    }

    @SuppressWarnings("all")
    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @SuppressWarnings("all")
    public void setValidated(final boolean validated) {
        this.validated = validated;
    }
}
